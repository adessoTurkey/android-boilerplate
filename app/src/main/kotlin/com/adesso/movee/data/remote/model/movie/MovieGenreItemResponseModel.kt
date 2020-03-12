package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.local.model.MovieGenreLocalModel
import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.uimodel.MovieGenreUiModel
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieGenreItemResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String
) : BaseResponseModel() {

    fun toUiModel() = MovieGenreUiModel(id = id, name = name)

    fun toCacheModel() = MovieGenreLocalModel(id = id, name = name)
}
