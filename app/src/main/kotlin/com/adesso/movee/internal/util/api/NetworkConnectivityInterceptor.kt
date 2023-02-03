package com.adesso.movee.internal.util.api

import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.NetworkConnectivityListener
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class NetworkConnectivityInterceptor @Inject constructor(
    private val networkConnectivityListener: NetworkConnectivityListener
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!networkConnectivityListener.isConnected) {
            Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .code(Failure.NoConnectivityError.errorCode)
                .message(Failure.NoConnectivityError.errorMessage)
                .body(Failure.NoConnectivityError.errorMessage.toResponseBody(null))
                .build()
        } else {
            chain.proceed(chain.request())
        }
    }
}
