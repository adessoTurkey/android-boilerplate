package com.adesso.movee.base

import android.app.Application
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.adesso.movee.application.MainApplication
import javax.inject.Inject

class ResProvider @Inject constructor(val application: Application) {

    fun getString(@StringRes id: Int) =
        (application as MainApplication).currentActivity.getString(id)

    fun getString(@StringRes id: Int, vararg params: String) = String.format(getString(id), *params)

    fun getInteger(@IntegerRes id: Int) =
        (application as MainApplication).currentActivity.resources.getInteger(id)

    fun getStringArray(@ArrayRes id: Int): Array<String> =
        (application as MainApplication).currentActivity.resources.getStringArray(id)

    fun getDrawable(@DrawableRes id: Int) =
        ContextCompat.getDrawable(application.applicationContext, id)

    fun getColor(@ColorRes id: Int) = ContextCompat.getColor(application.applicationContext, id)

    fun getColorStateList(@ColorRes id: Int) =
        ContextCompat.getColorStateList(application.applicationContext, id)

    fun getDimension(@DimenRes id: Int) = application.applicationContext.resources.getDimension(id)
}
