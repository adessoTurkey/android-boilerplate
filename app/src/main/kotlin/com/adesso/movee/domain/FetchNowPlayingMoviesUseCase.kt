package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.scene.movielist.model.MovieUiModel
import javax.inject.Inject

class FetchNowPlayingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<List<MovieUiModel>, UseCase.None>() {

    override suspend fun buildUseCase(params: None) = repository.fetchNowPlayingMovies()
}
