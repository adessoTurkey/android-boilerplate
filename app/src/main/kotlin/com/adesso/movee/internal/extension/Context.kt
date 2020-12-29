package com.adesso.movee.internal.extension

import android.content.Context
import android.widget.Toast
import com.adesso.movee.internal.popup.Popup
import com.adesso.movee.internal.popup.PopupListener
import com.adesso.movee.internal.popup.PopupModel

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Context.showPopup(model: PopupModel, listener: PopupListener? = null) {
    Popup(this, model, listener).show()
}
