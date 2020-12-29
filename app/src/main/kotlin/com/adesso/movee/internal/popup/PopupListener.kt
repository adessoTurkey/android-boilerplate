package com.adesso.movee.internal.popup

class PopupListener(
    val onPositiveButtonClick: (() -> Unit)? = null,
    val onNegativeButtonClick: (() -> Unit)? = null
)
