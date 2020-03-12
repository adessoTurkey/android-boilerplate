package com.adesso.movee.internal.util

import timber.log.Timber

object TimberTree {
    val debug = object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            return String.format(
                "[%s:%s]",
                super.createStackElementTag(element),
                element.lineNumber
            )
        }
    }
}
