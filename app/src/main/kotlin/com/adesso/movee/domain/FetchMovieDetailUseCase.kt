package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import javax.inject.Inject

class FetchMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<MovieDetailUiModel, FetchMovieDetailUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.fetchMovieDetail(params.id)

    data class Params(val id: Long)
}
