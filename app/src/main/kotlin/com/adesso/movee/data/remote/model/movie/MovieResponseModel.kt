package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.MovieGenreUiModel
import com.adesso.movee.uimodel.MovieUiModel
import com.squareup.moshi.Json
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "genre_ids") val genreIds: List<Long>,
    @Json(name = "poster_path") @Image val posterPath: String?,
    @Json(name = "backdrop_path") @Image val backdropPath: String?,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "vote_average") val average: Double,
    @Json(name = "adult") val isAdult: Boolean,
    @Json(name = "release_date") val releaseDate: Date?
) : BaseResponseModel() {

    fun toUiModel(genres: List<MovieGenreUiModel>) = MovieUiModel(
        id = id,
        title = title,
        overview = overview,
        genres = genres,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        isAdult = isAdult,
        releaseDate = releaseDate
    )
}
