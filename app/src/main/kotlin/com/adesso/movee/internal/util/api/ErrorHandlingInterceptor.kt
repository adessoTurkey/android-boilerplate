package com.adesso.movee.internal.util.api

import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.NetworkHandler
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException

class ErrorHandlingInterceptor(
    private val networkHandler: NetworkHandler,
    private val moshi: Moshi
) : Interceptor {

    @Throws(IOException::class, Failure::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHandler.isConnected) {
            throw Failure.NoConnectivityError
        }

        val response = try {
            chain.proceed(chain.request())
        } catch (e: Exception) { // https://github.com/square/okhttp/issues/4380
            throw when (e) {
                is UnknownHostException -> Failure.UnknownHostError()
                is HttpException -> Failure.HttpError(e.code(), e.message())
                is SocketTimeoutException -> Failure.TimeOutError(e.message)
                else -> IOException(e)
            }
        }

        if (response.isSuccessful) {

            if (response.body() == null) {
                throw Failure.EmptyResponse
            }
            return response
        } else {
            val responseJson = response.body()?.string()
                ?: throw Failure.ApiError(
                    code = response.code(),
                    message = response.message() ?: UNKNOWN_ERROR
                )
            val apiError = moshi
                .adapter<ApiError>(ApiError::class.java)
                .fromJson(responseJson)

            throw Failure.ApiError(
                code = apiError?.code ?: response.code(),
                message = apiError?.message ?: UNKNOWN_ERROR
            )
        }
    }

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}

private data class ApiError(
    @Json(name = "status_code") val code: Int,
    @Json(name = "status_message") val message: String
)
