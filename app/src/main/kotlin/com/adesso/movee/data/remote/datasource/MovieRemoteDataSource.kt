package com.adesso.movee.data.remote.datasource

import com.adesso.movee.data.remote.BaseRemoteDataSource
import com.adesso.movee.data.remote.api.MovieService
import com.adesso.movee.data.remote.model.movie.MovieGenreResponseModel
import com.adesso.movee.data.remote.model.movie.NowPlayingMovieResponseModel
import com.adesso.movee.data.remote.model.movie.PopularMovieResponseModel
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService
) : BaseRemoteDataSource() {

    suspend fun fetchPopularMovies(): PopularMovieResponseModel = invoke {
        service.fetchPopularMovies()
    }

    suspend fun fetchMovieGenres(): MovieGenreResponseModel = invoke {
        service.fetchMovieGenres()
    }

    suspend fun fetchNowPlayingMovies(): NowPlayingMovieResponseModel = invoke {
        service.fetchNowPlayingMovies()
    }
}
