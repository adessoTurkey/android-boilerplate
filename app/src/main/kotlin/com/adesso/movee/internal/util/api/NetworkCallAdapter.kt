package com.adesso.movee.internal.util.api

import com.adesso.movee.internal.util.Failure
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber

class NetworkCallAdapter : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Result::class.java) return null
        check(responseType is ParameterizedType) { "Response type must be a parameterized type." }

        val rightType = getParameterUpperBound(0, responseType)

        val errorConverter = retrofit.nextResponseBodyConverter<Failure.NetworkError>(
            null,
            Failure.NetworkError::class.java,
            annotations
        )
        return ResultCallAdapter<Any>(rightType, errorConverter)
    }
}

class ResultCallAdapter<R>(
    private val type: Type,
    private val errorConverter: Converter<ResponseBody, Failure.NetworkError>
) : CallAdapter<R, Call<Result<R, Failure>>> {

    override fun responseType(): Type = type

    override fun adapt(call: Call<R>): Call<Result<R, Failure>> = ResultCall(call, type, errorConverter)
}

private class ResultCall<R> constructor(
    private val delegate: Call<R>,
    private val successType: Type,
    private val errorConverter: Converter<ResponseBody, Failure.NetworkError>
) : Call<Result<R, Failure>> {

    override fun enqueue(callback: Callback<Result<R, Failure>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@ResultCall, Response.success(response.toResult()))
            }

            private fun Response<R>.toResult(): Result<R, Failure> {
                if (!isSuccessful) {
                    val failure =
                        if (Failure.NoConnectivityError.errorCode == code() &&
                            Failure.NoConnectivityError.errorMessage == message()
                        ) {
                            Failure.NoConnectivityError
                        } else {
                            val errorBody = errorBody()?.let { errorConverter.convert(it) }
                            Failure.NetworkError(errorBody?.message ?: "")
                        }

                    return Err(failure)
                }

                // Http success response with body
                body()?.let { body -> return Ok(body) }

                // if we defined Unit as success type it means we expected no response body
                // e.g. in case of 204 No Content
                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    Ok(Unit) as Result<R, Failure>
                } else {
                    Err(Failure.EmptyResponse)
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                Timber.e(throwable)
                val error = when (throwable) {
                    is IOException -> Failure.NetworkError(throwable.message ?: "")
                    else -> Failure.UnknownError(throwable.message ?: "")
                }
                callback.onResponse(this@ResultCall, Response.success(Err(error)))
            }
        }
    )

    override fun clone(): Call<Result<R, Failure>> = ResultCall(delegate.clone(), successType, errorConverter)

    override fun execute(): Response<Result<R, Failure>> = throw UnsupportedOperationException()

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
