package com.adesso.movee.data.repository

import com.adesso.movee.data.remote.datasource.MovieRemoteDataSource
import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.remote.model.movie.MovieResponseModel
import com.adesso.movee.data.remote.model.movie.NowPlayingMovieResponseModel
import com.adesso.movee.data.repository.mapper.MovieMapper
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieUiModel
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieRepositoryTest {

    private val remoteDataSource = mockk<MovieRemoteDataSource>()
    private val mapper = mockk<MovieMapper>()

    private val repository = MovieRepository(remoteDataSource, mapper)

    private val movieResponseModel = MovieResponseModel(
        id = 0,
        title = "",
        overview = "",
        genreIds = listOf(),
        posterPath = null,
        backdropPath = null,
        popularity = 0.0,
        average = 0.0,
        isAdult = false,
        releaseDate = null
    )

    private val movieUiModel = MovieUiModel(
        id = 0,
        title = "",
        overview = "",
        posterPath = null,
        average = 0.0,
        releaseDate = null,
        backdropPath = null,
        popularity = 0.0,
        isAdult = false
    )

    private val movieDetailResponseModel = MovieDetailResponseModel(
        id = 0,
        title = "",
        overview = "",
        genres = listOf(),
        runtime = null,
        posterPath = null,
        popularity = 0.0,
        average = 0.0,
        releaseDate = null
    )

    private val movieDetailUiModel = MovieDetailUiModel(
        id = 0,
        title = "",
        overview = "",
        posterPath = null,
        average = "",
        runtime = "",
        releaseDate = "",
        popularity = "",
        genres = listOf()
    )

    @Test
    fun `fetchNowPlayingMovies should return list of movieUiModel`() = runBlocking {
        val nowPlayingMovies = NowPlayingMovieResponseModel(
            movieList = listOf(movieResponseModel)
        )
        coEvery { remoteDataSource.fetchNowPlayingMovies() } returns Ok(nowPlayingMovies)
        coEvery { mapper.mapMovie(any()) } returns movieUiModel

        val actual: Result<List<MovieUiModel>, Failure> = repository.fetchNowPlayingMovies()

        assertTrue(actual is Ok)
        assertEquals(actual.get(), listOf(movieUiModel))
    }

    @Test
    fun `fetchMovieDetail should return movieDetailUiModel`() = runBlocking {
        coEvery { remoteDataSource.fetchMovieDetail(123L) } returns Ok(movieDetailResponseModel)
        coEvery { mapper.mapMovieDetail(any()) } returns movieDetailUiModel

        val actual = repository.fetchMovieDetail(123L)

        assertTrue(actual is Ok)
        assertEquals(actual.get(), movieDetailUiModel)
    }

    @Test
    fun `fetchMovieDetail should return Failure`() = runBlocking {
        val unknownErrorMessage = "UnknownError"
        coEvery { remoteDataSource.fetchMovieDetail(any()) } returns Err(Failure.UnknownError(unknownErrorMessage))

        val actual = repository.fetchMovieDetail(123L)

        assertTrue(actual is Err)
        assertNotNull(actual.getError())
        assertEquals(actual.getError()?.message, unknownErrorMessage)
    }
}
