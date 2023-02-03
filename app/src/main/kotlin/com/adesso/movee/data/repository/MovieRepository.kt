package com.adesso.movee.data.repository

import com.adesso.movee.data.remote.datasource.MovieRemoteDataSource
import com.adesso.movee.data.repository.mapper.MovieMapper
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieUiModel
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieMapper
) {

    suspend fun fetchNowPlayingMovies(): Result<List<MovieUiModel>, Failure> {
        return remoteDataSource.fetchNowPlayingMovies()
            .map { responseModel -> responseModel.movieList.map { movie -> mapper.mapMovie(movie) } }
    }

    suspend fun fetchMovieDetail(id: Long): Result<MovieDetailUiModel, Failure> {
        return remoteDataSource.fetchMovieDetail(id)
            .map { mapper.mapMovieDetail(it) }
    }
}
