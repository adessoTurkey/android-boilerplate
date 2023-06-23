package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.scene.movielist.model.MovieUiModel
import com.github.michaelbull.result.Result
import javax.inject.Inject

class FetchNowPlayingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<List<MovieUiModel>, UseCase.None>() {

    override suspend fun run(params: None): Result<List<MovieUiModel>, Failure> = repository.fetchNowPlayingMovies()
}
