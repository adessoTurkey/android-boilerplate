package com.adesso.movee.data.local.model

import com.adesso.movee.data.local.BaseLocalModel
import com.adesso.movee.uimodel.MovieGenreUiModel

data class MovieGenreLocalModel(
    val id: Long,
    val name: String
) : BaseLocalModel() {

    fun toUiModel() = MovieGenreUiModel(id = id, name = name)
}
