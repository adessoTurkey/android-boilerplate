package com.adesso.movee.uimodel

import android.os.Parcelable

interface CreditUiModel : Parcelable {

    val creditId: String

    val id: Long

    val name: String

    val profilePath: String?
}
