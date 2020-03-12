package com.adesso.movee.uimodel

import android.os.Parcelable
import com.adesso.movee.base.ListAdapterItem
import com.adesso.movee.internal.extension.formatDate
import java.util.Date

interface ShowUiModel : ListAdapterItem, Parcelable {
    override val id: Long

    val title: String

    val overview: String

    val genres: List<GenreUiModel>

    val posterPath: String?

    val average: Double

    val releaseDate: Date?

    val averageString: String get() = average.toString()

    val releaseDateString: String
        get() = releaseDate?.formatDate(DATE_FORMAT_SHOW) ?: ""

    val genreString: String
        get() = genres.joinToString(separator = ", ", limit = 3, truncated = "") { it.name }

    companion object {
        const val DATE_FORMAT_SHOW = "dd.MM.yyyy"
    }
}
