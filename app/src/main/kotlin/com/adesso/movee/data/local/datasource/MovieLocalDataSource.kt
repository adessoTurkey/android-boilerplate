package com.adesso.movee.data.local.datasource

import android.content.SharedPreferences
import com.adesso.movee.data.local.delegate.listPreference
import com.adesso.movee.data.local.model.MovieGenreLocalModel
import com.squareup.moshi.Moshi
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    sharedPreferences: SharedPreferences,
    moshi: Moshi
) {

    private var genres: List<MovieGenreLocalModel>? by listPreference(
        moshi = moshi,
        preferenceName = PREF_GENRES,
        preferences = sharedPreferences
    )

    fun fetchGenres(): List<MovieGenreLocalModel>? {
        return genres
    }

    fun insertGenres(genreList: List<MovieGenreLocalModel>?) {
        genres = genreList
    }

    companion object {
        private const val PREF_GENRES = "movie_genres"
    }
}
