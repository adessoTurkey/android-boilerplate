package com.adesso.movee.internal.popup

import androidx.annotation.Keep
import java.io.Serializable

@Keep
open class PopupUiModel(
    var title: String? = null,
    var message: String? = null,
    var editableHint: String? = null,
    var iconResourceId: Int = 0,
    var bgResourceId: Int = 0,
    var cancelable: Boolean = false,
    val addCancelButton: Boolean = false,
    var cancelButtonText: String? = null,
    var confirmButtonText: String? = null,
    val popUpType: PopUpType = PopUpType.WARNING
) : Serializable {
    val bgResource: Int
        get() = if (bgResourceId == 0) popUpType.bgRes else bgResourceId

    val iconResource: Int get() = popUpType.iconRes
}

@Keep
interface PopupCallback : Serializable {
    fun onConfirmClick()
    fun onCancelClick()
    fun onDismiss()
}
