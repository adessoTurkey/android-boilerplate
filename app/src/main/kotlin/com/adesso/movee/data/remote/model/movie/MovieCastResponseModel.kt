package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.MovieCastUiModel
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieCastResponseModel(
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "cast_id") val castId: Long,
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "profile_path") @Image val profilePath: String?,
    @Json(name = "character") val character: String
) : BaseResponseModel() {

    fun toUiModel() = MovieCastUiModel(
        creditId = creditId,
        castId = castId,
        id = id,
        name = name,
        profilePath = profilePath,
        character = character
    )
}
