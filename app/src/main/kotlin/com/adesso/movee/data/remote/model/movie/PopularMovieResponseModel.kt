package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PopularMovieResponseModel(
    @Json(name = "results") val movieList: List<MovieResponseModel>
) : BaseResponseModel()
