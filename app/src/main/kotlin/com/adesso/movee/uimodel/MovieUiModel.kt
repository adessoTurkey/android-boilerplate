package com.adesso.movee.uimodel

import com.adesso.movee.base.ListAdapterItem
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUiModel(
    override val id: Long,
    override val title: String,
    override val overview: String,
    override val genres: List<MovieGenreUiModel>,
    override val posterPath: String?,
    override val average: Double,
    override val releaseDate: Date?,
    val backdropPath: String?,
    val popularity: Double,
    val isAdult: Boolean
) : ShowUiModel, ListAdapterItem
