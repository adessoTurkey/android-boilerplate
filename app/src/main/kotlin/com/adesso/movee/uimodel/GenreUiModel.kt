package com.adesso.movee.uimodel

import android.os.Parcelable
import com.adesso.movee.base.ListAdapterItem

interface GenreUiModel : ListAdapterItem, Parcelable {

    override val id: Long

    val name: String
}
