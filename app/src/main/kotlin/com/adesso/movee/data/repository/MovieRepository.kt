package com.adesso.movee.data.repository

import com.adesso.movee.data.remote.datasource.MovieRemoteDataSource
import com.adesso.movee.data.repository.mapper.MovieMapper
import com.adesso.movee.internal.extension.mapSuccess
import com.adesso.movee.internal.util.api.State
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieUiModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieMapper
) {

    suspend fun fetchNowPlayingMovies(): List<MovieUiModel> {
        val moviesResponse = remoteDataSource.fetchNowPlayingMovies()

        return moviesResponse.movieList.map { mapper.toUiModel(it) }
    }

    suspend fun fetchMovieDetailFlow(id: Long): Flow<State<MovieDetailUiModel>> {
        return remoteDataSource.fetchMovieDetailFlow(id).mapSuccess {
            mapper.toUiModel(it)
        }
    }
}
