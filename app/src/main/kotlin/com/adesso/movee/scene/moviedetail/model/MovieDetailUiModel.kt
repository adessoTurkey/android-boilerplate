package com.adesso.movee.scene.moviedetail.model

import android.os.Parcelable
import com.adesso.movee.scene.movielist.model.MovieGenreItemUiModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailUiModel(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val average: String,
    val runtime: String,
    val releaseDate: String,
    val popularity: String,
    val genres: List<MovieGenreItemUiModel>
) : Parcelable {
    val genreString get() = genres.joinToString(", ") { it.name }
}
