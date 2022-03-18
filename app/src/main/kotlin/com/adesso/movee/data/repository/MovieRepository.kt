package com.adesso.movee.data.repository

import com.adesso.movee.data.remote.datasource.MovieRemoteDataSource
import com.adesso.movee.data.repository.mapper.MovieMapper
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieUiModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieMapper
) {

    suspend fun fetchNowPlayingMovies(): List<MovieUiModel> {
        val moviesResponse = remoteDataSource.fetchNowPlayingMovies()

        return moviesResponse.movieList.map { mapper.toUiModel(it) }
    }

    suspend fun fetchMovieDetail(id: Long): MovieDetailUiModel {
        val movieDetailResponse = remoteDataSource.fetchMovieDetail(id)

        return mapper.toUiModel(movieDetailResponse)
    }

    suspend fun fetchMovieDetailFlow(id: Long): Flow<MovieDetailUiModel> = flow {
        remoteDataSource.fetchMovieDetailFlow(id)
            .collect {
                emit(mapper.toUiModel(it))
            }
    }
}
