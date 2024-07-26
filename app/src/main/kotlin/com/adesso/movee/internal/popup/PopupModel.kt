package com.adesso.movee.internal.popup

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
class PopupModel(
    var title: String? = null,
    @StringRes var titleRes: Int? = null,
    var message: String? = null,
    @StringRes var messageRes: Int? = null,
    var cancelable: Boolean = false,
    var positiveButtonText: String? = null,
    var negativeButtonText: String? = null
) : Parcelable
