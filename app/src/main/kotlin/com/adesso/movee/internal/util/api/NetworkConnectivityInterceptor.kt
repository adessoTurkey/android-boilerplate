package com.adesso.movee.internal.util.api

import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.NetworkConnectivityListener
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectivityInterceptor @Inject constructor(
    private val networkConnectivityListener: NetworkConnectivityListener
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!networkConnectivityListener.isConnected) {
            throw Failure.NoConnectivityError
        } else {
            chain.proceed(chain.request())
        }
    }
}
