package com.adesso.movee.data.remote.datasource

import com.adesso.movee.data.remote.BaseRemoteDataSource
import com.adesso.movee.data.remote.api.MovieService
import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.remote.model.movie.NowPlayingMovieResponseModel
import com.adesso.movee.internal.util.Failure
import com.github.michaelbull.result.Result
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService
) : BaseRemoteDataSource() {

    suspend fun fetchNowPlayingMovies(): Result<NowPlayingMovieResponseModel, Failure> = invoke {
        service.fetchNowPlayingMovies()
    }

    suspend fun fetchMovieDetail(id: Long): Result<MovieDetailResponseModel, Failure> = invoke {
        service.fetchMovieDetail(id)
    }
}
