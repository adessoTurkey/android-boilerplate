package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.uimodel.MovieCreditUiModel
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieCreditsResponseModel(
    @Json(name = "cast") val cast: List<MovieCastResponseModel>,
    @Json(name = "crew") val crew: List<MovieCrewResponseModel>
) : BaseResponseModel() {

    fun toUiModel() = MovieCreditUiModel(
        cast = cast.map { it.toUiModel() },
        crew = crew.map { it.toUiModel() }
    )
}
