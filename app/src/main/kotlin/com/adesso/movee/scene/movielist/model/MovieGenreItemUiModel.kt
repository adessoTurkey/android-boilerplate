package com.adesso.movee.scene.movielist.model

import android.os.Parcelable
import com.adesso.movee.base.ListAdapterItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieGenreItemUiModel(
    override val id: Long,
    val name: String
) : ListAdapterItem, Parcelable
