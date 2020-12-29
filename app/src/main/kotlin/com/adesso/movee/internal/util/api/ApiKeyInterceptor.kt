package com.adesso.movee.internal.util.api

import com.adesso.movee.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url
            .newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY)
            .build()

        val requestBuilder = request.newBuilder()
            .url(url)

        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val API_KEY = "api_key"
    }
}
