package com.adesso.movee.base

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.adesso.movee.R
import com.google.android.material.snackbar.Snackbar

interface SnackBarHelper {

    var Fragment.snackbar: Snackbar?

    fun Fragment.showSnackBarMessage(message: String) {
        val view = view ?: return

        snackbar?.dismiss()
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            show()
        }
    }
}
