package com.adesso.movee.internal.util.api

import com.adesso.movee.internal.util.Failure
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
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkCallAdapter : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != State::class.java) return null
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
) : CallAdapter<R, Call<State<R>>> {

    override fun responseType(): Type = type

    override fun adapt(call: Call<R>): Call<State<R>> = ResultCall(call, type, errorConverter)
}

private class ResultCall<R> constructor(
    private val delegate: Call<R>,
    private val successType: Type,
    private val errorConverter: Converter<ResponseBody, Failure.NetworkError>
) : Call<State<R>> {

    override fun enqueue(callback: Callback<State<R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@ResultCall, Response.success(response.toResult()))
            }

            private fun Response<R>.toResult(): State<R> {
                if (!isSuccessful) {
                    val errorBody = errorBody()?.let { errorConverter.convert(it) }

                    return State.Fail(Failure.NetworkError(errorBody?.message ?: ""))
                }

                // Http success response with body
                body()?.let { body -> return State.Success<R>(body) }

                // if we defined Unit as success type it means we expected no response body
                // e.g. in case of 204 No Content
                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    State.Success(Unit) as State.Success<R>
                } else {
                    @Suppress("UNCHECKED_CAST")
                    State.Fail(Failure.EmptyResponse)
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                val error = when (throwable) {
                    is IOException -> State.Fail(Failure.NetworkError(throwable.message ?: ""))
                    else -> State.Fail<R>(Failure.UnknownError(Exception(throwable)))
                }
                Timber.e(throwable)
                callback.onResponse(this@ResultCall, Response.success(error))
            }
        }
    )

    override fun clone(): Call<State<R>> = ResultCall(delegate.clone(), successType, errorConverter)

    override fun execute(): Response<State<R>> = throw UnsupportedOperationException()

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}

sealed class State<out T> {
    class Success<T>(val data: T) : State<T>()
    class Fail<T>(val failure: Failure) : State<T>()
}
