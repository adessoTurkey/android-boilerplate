package com.adesso.movee.internal.util

import android.content.Context
import com.adesso.movee.internal.extension.networkInfo
import javax.inject.Singleton

@Singleton
class NetworkHandler(private val context: Context) {
    val isConnected: Boolean
        get() = context.networkInfo?.isConnected ?: false
}
