package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.MovieCrewUiModel
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieCrewResponseModel(
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "department") val department: String,
    @Json(name = "id") val id: Long,
    @Json(name = "job") val job: String,
    @Json(name = "name") val name: String,
    @Json(name = "profile_path") @Image val profilePath: String?
) : BaseResponseModel() {

    fun toUiModel() = MovieCrewUiModel(
        creditId = creditId,
        id = id,
        name = name,
        department = department,
        job = job,
        profilePath = profilePath
    )
}
