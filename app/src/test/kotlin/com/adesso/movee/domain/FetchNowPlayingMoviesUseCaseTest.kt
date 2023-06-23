package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.scene.movielist.model.MovieUiModel
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
class FetchNowPlayingMoviesUseCaseTest {
    private val movieRepository = mockk<MovieRepository>()
    private val testDispatcher = TestCoroutineDispatcher()
    private val fetchNowPlayingMoviesUseCase = FetchNowPlayingMoviesUseCase(movieRepository)

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
    fun `when fetchNowPlayingMovies returns success, useCase returns success`() {
        runBlocking {
            val movieUiModels = listOf(
                MovieUiModel(
                    id = 1,
                    title = "",
                    overview = "",
                    posterPath = null,
                    average = 0.0,
                    releaseDate = null,
                    backdropPath = null,
                    popularity = 0.0,
                    isAdult = false,
                ),
                MovieUiModel(
                    id = 2,
                    title = "",
                    overview = "",
                    posterPath = null,
                    average = 0.0,
                    releaseDate = null,
                    backdropPath = null,
                    popularity = 0.0,
                    isAdult = false
                )
            )

            val expectedResult: Result<List<MovieUiModel>, Nothing> = Ok(movieUiModels)
            coEvery { movieRepository.fetchNowPlayingMovies() } returns expectedResult

            // act
            lateinit var actualResult: Result<List<MovieUiModel>, Failure>

            fetchNowPlayingMoviesUseCase(UseCase.None) { result: Result<List<MovieUiModel>, Failure> ->
                actualResult = result
            }

            // assert
            assertEquals(expectedResult, actualResult)
        }
    }
}
