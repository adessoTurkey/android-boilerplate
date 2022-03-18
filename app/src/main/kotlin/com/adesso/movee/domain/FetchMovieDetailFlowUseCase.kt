package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.flow.FlowUseCase
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FetchMovieDetailFlowUseCase @Inject constructor(
    private val repository: MovieRepository
) : FlowUseCase<FetchMovieDetailFlowUseCase.Params, MovieDetailUiModel>() {

    data class Params(val id: Long)

    override suspend fun execute(params: Params): Flow<MovieDetailUiModel> =
        repository.fetchMovieDetailFlow(params.id)
}
