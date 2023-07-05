package com.adesso.movee.scene.movielist.model

import android.os.Parcelable
import com.adesso.movee.base.ListAdapterItem
import com.adesso.movee.internal.extension.formatDate
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MovieUiModel(
    override val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val average: Double,
    val releaseDate: LocalDate?,
    val backdropPath: String?,
    val popularity: Double,
    val isAdult: Boolean
) : ListAdapterItem, Parcelable {
    val averageString get() = average.toString()
    val releaseDateString get() = releaseDate?.formatDate(DATE_FORMAT_MOVIE) ?: ""

    companion object {
        const val DATE_FORMAT_MOVIE = "dd.MM.yyyy"
    }
}
