package com.adesso.movee.data.remote.api

import com.adesso.movee.data.remote.model.movie.MovieCreditsResponseModel
import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.remote.model.movie.MovieGenreResponseModel
import com.adesso.movee.data.remote.model.movie.NowPlayingMovieResponseModel
import com.adesso.movee.data.remote.model.movie.PopularMovieResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET(POPULAR)
    suspend fun fetchPopularMovies(): PopularMovieResponseModel

    @GET(GENRE)
    suspend fun fetchMovieGenres(): MovieGenreResponseModel

    @GET(NOW_PLAYING)
    suspend fun fetchNowPlayingMovies(): NowPlayingMovieResponseModel

    @GET(DETAIL)
    suspend fun fetchMovieDetail(@Path(PATH_MOVIE_ID) id: Long): MovieDetailResponseModel

    @GET(CREDIT)
    suspend fun fetchCredits(@Path(PATH_MOVIE_ID) id: Long): MovieCreditsResponseModel

    companion object {
        const val POPULAR = "movie/popular"
        const val GENRE = "genre/movie/list"
        const val NOW_PLAYING = "movie/now_playing"
        const val PATH_MOVIE_ID = "movie_id"
        const val DETAIL = "movie/{$PATH_MOVIE_ID}"
        const val CREDIT = "movie/{$PATH_MOVIE_ID}/credits"
    }
}
