package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieGenreItemUiModel
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchMovieDetailUseCaseTest {

    private val movieRepository = mockk<MovieRepository>()
    private val testDispatcher = TestCoroutineDispatcher()
    private val fetchMovieDetailUseCase = FetchMovieDetailUseCase(movieRepository)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        clearMocks(movieRepository)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetchMovieDetail returns success, useCase returns success`() {
        runBlocking {
            val detailUiModel = MovieDetailUiModel(
                id = 100,
                title = "Joker",
                overview = "Joker Overview",
                posterPath = "https...",
                average = "Joker Average",
                runtime = "122 min",
                releaseDate = "10.07.1990",
                popularity = "9.0",
                genres = listOf(MovieGenreItemUiModel(id = 122, name = "Drama"))
            )

            val expectedResult: Result<MovieDetailUiModel, Failure> = Ok(detailUiModel)
            coEvery { movieRepository.fetchMovieDetail(any()) } returns expectedResult


            // act
            lateinit var actualResult: Result<MovieDetailUiModel, Failure>

            fetchMovieDetailUseCase(FetchMovieDetailUseCase.Params(1)) { result: Result<MovieDetailUiModel, Failure> ->
                actualResult = result
            }

            // assert
            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `when fetchMovieDetail returns failure, useCase returns failure`() {
        runBlocking {
            val expectedResult = Err(Failure.UnknownError(""))
            coEvery { movieRepository.fetchMovieDetail(any()) } returns expectedResult

            // act
            lateinit var actualResult: Result<MovieDetailUiModel, Failure>

            fetchMovieDetailUseCase(FetchMovieDetailUseCase.Params(1)) { result: Result<MovieDetailUiModel, Failure> ->
                actualResult = result
            }

            // assert
            assertEquals(expectedResult, actualResult)
        }
    }
}
