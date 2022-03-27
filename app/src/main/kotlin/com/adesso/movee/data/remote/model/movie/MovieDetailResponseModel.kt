package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.squareup.moshi.Json
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "genres") val genres: List<MovieGenreItemResponseModel>,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "poster_path") @Image
    val posterPath: String?,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "vote_average") val average: Double,
    @Json(name = "release_date") val releaseDate: Date?
) : BaseResponseModel()
