package com.papara.merchant.internal.util

import android.content.Context
import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String

    fun getString(@StringRes resId: Int): String
}

class ResourceProviderImpl(val context: Context) : ResourceProvider {

    override fun getString(@StringRes resId: Int): String {
        return context.resources.getString(resId)
    }

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}
