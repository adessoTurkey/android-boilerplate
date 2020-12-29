package com.adesso.movee.data.repository.mapper

import com.adesso.movee.data.remote.model.movie.MovieGenreItemResponseModel
import com.adesso.movee.scene.movielist.model.MovieGenreItemUiModel
import javax.inject.Inject

class GenreMapper @Inject constructor() {
    fun toUiModel(responseModel: MovieGenreItemResponseModel) = with(responseModel) {
        MovieGenreItemUiModel(id, name)
    }
}
