package com.adesso.movee.uimodel

import com.adesso.movee.base.ListAdapterItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieGenreUiModel(
    override val id: Long,
    override val name: String
) : GenreUiModel, ListAdapterItem
