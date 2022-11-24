package com.adesso.movee.data.remote.datasource

import com.adesso.movee.data.remote.BaseRemoteDataSource
import com.adesso.movee.data.remote.api.MovieService
import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.remote.model.movie.NowPlayingMovieResponseModel
import com.adesso.movee.internal.util.api.State
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService
) : BaseRemoteDataSource() {

    suspend fun fetchNowPlayingMovies(): NowPlayingMovieResponseModel = invoke {
        service.fetchNowPlayingMovies()
    }

    suspend fun fetchMovieDetailFlow(id: Long): Flow<State<MovieDetailResponseModel>> = invokeFlow {
        service.fetchMovieDetail(id)
    }
}
