package com.papara.merchant.internal.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDate(format: String, locale: Locale = Locale.ENGLISH): String {
    val dateFormat = SimpleDateFormat(format, locale)
    return dateFormat.format(this)
}
