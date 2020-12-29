package com.adesso.movee.data.repository.mapper

import com.adesso.movee.R
import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.remote.model.movie.MovieResponseModel
import com.adesso.movee.internal.extension.formatDate
import com.adesso.movee.internal.extension.toDecimalizedString
import com.adesso.movee.internal.util.ResourceProvider
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieUiModel
import javax.inject.Inject

class MovieMapper @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val genreMapper: GenreMapper
) {

    fun toUiModel(responseModel: MovieResponseModel) = with(responseModel) {
        MovieUiModel(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            popularity = popularity,
            average = average,
            isAdult = isAdult,
            releaseDate = releaseDate
        )
    }

    fun toUiModel(responseModel: MovieDetailResponseModel) = with(responseModel) {
        val runtimeString = runtime?.let { runtime ->
            resourceProvider.getString(R.string.movie_detail_message_min_formatted, runtime)
        } ?: resourceProvider.getString(R.string.movie_detail_message_not_specified)

        MovieDetailUiModel(
            id = id,
            title = title,
            overview = overview,
            genres = genres.map { genreMapper.toUiModel(it) },
            posterPath = posterPath,
            popularity = popularity.toDecimalizedString(1),
            average = average.toDecimalizedString(1),
            runtime = runtimeString,
            releaseDate = releaseDate?.formatDate(DATE_FORMAT_MOVIE) ?: ""
        )
    }

    companion object {
        const val DATE_FORMAT_MOVIE = "dd.MM.yyyy"
    }
}
