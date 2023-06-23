package com.adesso.movee.data.remote.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adesso.movee.data.remote.api.MovieService
import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.remote.model.movie.MovieGenreItemResponseModel
import com.adesso.movee.internal.util.Failure
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class MovieRemoteDataSourceTest {

    private val mockMovieDetailResponseModel = MovieDetailResponseModel(
        id = 100,
        title = "Joker",
        overview = "Joker Overview",
        genres = listOf(MovieGenreItemResponseModel(id = 122, name = "Drama")),
        runtime = 122,
        posterPath = "https...",
        popularity = 21.0,
        average = 9.0,
        releaseDate = LocalDate.now()
    )

    private val apiErrorMessage = "Undefined Movie ID"

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var remoteDataSource: MovieRemoteDataSource

    @MockK
    lateinit var service: MovieService

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        remoteDataSource = MovieRemoteDataSource(service)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetch movie detail called successfully, it should return detail response model`() = runBlocking {
        val movieId = 100L

        // given
        coEvery { service.fetchMovieDetail(movieId) } coAnswers {
            Ok(mockMovieDetailResponseModel)
        }

        // then
        val result: Result<MovieDetailResponseModel, Failure> = remoteDataSource.fetchMovieDetail(movieId)
        assertTrue(result is Ok)
        assertEquals(result.get(), mockMovieDetailResponseModel)
    }

    @Test
    fun `when fetch movie detail called with wrong id should throw failure model`(): Unit = runBlocking {
        val movieId = -1L

        // given
        coEvery { service.fetchMovieDetail(movieId) } coAnswers {
            throw Failure.NetworkError(apiErrorMessage)
        }

        // then
        try {
            remoteDataSource.fetchMovieDetail(movieId)
        } catch (e: Exception) {
            assertTrue(e is Failure.NetworkError)
            assertEquals(e.message, apiErrorMessage)
        }
    }
}
