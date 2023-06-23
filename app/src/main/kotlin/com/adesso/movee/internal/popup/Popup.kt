package com.adesso.movee.internal.popup

import android.app.AlertDialog
import android.content.Context

class Popup(
    context: Context,
    private val model: PopupModel,
    private val listener: PopupListener? = null
) {
    private val alertDialogBuilder = AlertDialog.Builder(context)

    init {
        with(alertDialogBuilder) {
            model.title?.let { setTitle(it) }
            model.titleInt?.let { setTitle(it) }
            model.message?.let { setMessage(it) }
            model.messageInt?.let { setMessage(it) }

            val cancelable = model.cancelable || listener == null

            setCancelable(cancelable)
            setButtons()
        }
    }

    private fun setButtons() = with(alertDialogBuilder) {
        val resources = alertDialogBuilder.context.resources
        listener?.let {
            val positiveButtonText =
                model.positiveButtonText ?: resources.getString(android.R.string.ok)
            val negativeButtonText =
                model.negativeButtonText ?: resources.getString(android.R.string.cancel)

            it.onPositiveButtonClick?.let {
                setPositiveButton(positiveButtonText) { _, _ -> it.invoke() }
            }
            it.onNegativeButtonClick?.let {
                setNegativeButton(negativeButtonText) { _, _ -> it.invoke() }
            }
        }
    }

    fun show() = alertDialogBuilder.show()
}
