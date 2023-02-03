package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.github.michaelbull.result.Result
import javax.inject.Inject

class FetchMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<MovieDetailUiModel, FetchMovieDetailUseCase.Params>() {

    data class Params(val id: Long)

    override suspend fun run(params: Params): Result<MovieDetailUiModel, Failure> =
        repository.fetchMovieDetail(params.id)
}
