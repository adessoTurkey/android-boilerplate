package com.papara.merchant.internal.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import java.lang.IllegalStateException

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class Image

class ImageJsonAdapter {

    @ToJson
    fun toJson(
        @Suppress("UNUSED_PARAMETER") @Image
        path: String
    ): String? {
        throw IllegalStateException("Image Json Adapter doesn't support to  convert to Json")
    }

    @FromJson
    @Image
    fun fromJson(path: String?): String? {
        return if (path.isNullOrBlank()) null else "$PREFIX_IMAGE_URL$path"
    }

    companion object {
        private const val PREFIX_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
