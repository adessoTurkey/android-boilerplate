package com.adesso.movee.internal.extension

import com.adesso.movee.internal.util.Constant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDate(format: String, locale: Locale = Constant.Locale.ENGLISH): String {
    val dateFormat = SimpleDateFormat(format, locale)
    return dateFormat.format(this)
}
