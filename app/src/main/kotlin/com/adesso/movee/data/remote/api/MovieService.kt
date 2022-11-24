package com.adesso.movee.data.remote.api

import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.remote.model.movie.NowPlayingMovieResponseModel
import com.adesso.movee.internal.util.api.State
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET(NOW_PLAYING)
    suspend fun fetchNowPlayingMovies(): NowPlayingMovieResponseModel

    @GET(DETAIL)
    suspend fun fetchMovieDetail(@Path(PATH_MOVIE_ID) id: Long): State<MovieDetailResponseModel>

    companion object {
        const val NOW_PLAYING = "movie/now_playing"
        const val PATH_MOVIE_ID = "movie_id"
        const val DETAIL = "movie/{$PATH_MOVIE_ID}"
    }
}
