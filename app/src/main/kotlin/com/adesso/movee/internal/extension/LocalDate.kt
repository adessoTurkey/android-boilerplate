package com.adesso.movee.internal.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.formatDate(format: String, locale: Locale = Locale.ENGLISH): String {
    val dateFormat = DateTimeFormatter.ofPattern(format, locale)
    return dateFormat.format(this)
}
