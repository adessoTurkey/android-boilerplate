package com.adesso.movee.uimodel

import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieCastUiModel(
    override val creditId: String,
    override val id: Long,
    override val name: String,
    override val profilePath: String?,
    val castId: Long,
    val character: String
) : CreditUiModel
