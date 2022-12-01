package com.adesso.movee.internal.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateAdapter {
    private val dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_SERVER, Locale.ENGLISH)

    @FromJson
    fun fromJson(date: String): LocalDate? {
        return try {
            LocalDate.parse(date, dateFormat)
        } catch (e: ParseException) {
            null
        }
    }

    @ToJson
    fun toJson(date: LocalDate): String {
        return dateFormat.format(date)
    }

    companion object {
        private const val DATE_FORMAT_SERVER = "yyyy-MM-dd"
    }
}
