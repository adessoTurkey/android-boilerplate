package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieGenreResponseModel(
    @Json(name = "genres") val genres: List<MovieGenreItemResponseModel>
) : BaseResponseModel()
