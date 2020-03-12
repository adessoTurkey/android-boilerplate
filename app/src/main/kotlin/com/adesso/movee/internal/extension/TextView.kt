package com.adesso.movee.internal.extension

import android.view.MotionEvent
import android.widget.TextView

fun TextView.setOnDrawableEndClickListener(fn: () -> Unit) {
    setOnTouchListener { _, event ->
        if (event?.action == MotionEvent.ACTION_UP &&
            event.x >= width - totalPaddingEnd
        ) {
            fn.invoke()
            true
        } else {
            false
        }
    }
}
