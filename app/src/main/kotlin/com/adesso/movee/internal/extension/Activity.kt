package com.adesso.movee.internal.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.closeKeyboard() {
    currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.showKeyboard() {
    currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}
