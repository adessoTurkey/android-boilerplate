package com.adesso.movee.internal.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class DateAdapter {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT_SERVER, Constant.Locale.ENGLISH)

    @FromJson
    fun fromJson(date: String): Date? {
        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            null
        }
    }

    @ToJson
    fun toJson(date: Date): String {
        return dateFormat.format(date)
    }

    companion object {
        private const val DATE_FORMAT_SERVER = "yyyy-MM-dd"
    }
}
