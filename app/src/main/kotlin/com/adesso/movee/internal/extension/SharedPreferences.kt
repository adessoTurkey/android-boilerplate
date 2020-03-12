package com.adesso.movee.internal.extension

import android.content.SharedPreferences
import androidx.core.content.edit

fun SharedPreferences.put(block: SharedPreferences.Editor.() -> Unit) {
    edit(commit = true) {
        block.invoke(this)
    }
}
