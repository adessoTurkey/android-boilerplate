package com.adesso.movee.data.remote.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adesso.movee.data.remote.api.MovieService
import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.remote.model.movie.MovieGenreItemResponseModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.api.State
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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
import java.util.Date

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
        releaseDate = Date(100020L)
    )

    private val apiErrorMessage = "Undefined Movie ID"
    private val mockMovieDetailFailureModel =
        Failure.NetworkError(message = apiErrorMessage)

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
            State.Success(mockMovieDetailResponseModel)
        }

        // then
        remoteDataSource.fetchMovieDetailFlow(movieId).collect { state ->
            assertTrue(state is State.Success)
            assertEquals(mockMovieDetailResponseModel, (state as State.Success).data)
        }
    }

    @Test
    fun `when fetch movie detail called with wrong id should throw failure model`() = runBlocking {
        val movieId = -1L

        // given
        coEvery { service.fetchMovieDetail(movieId) } coAnswers {
            throw Failure.NetworkError(apiErrorMessage)
        }

        // then
        remoteDataSource.fetchMovieDetailFlow(movieId).catch {
            assertTrue(it is Failure.NetworkError)

            val failure = it as? Failure.NetworkError

            assertEquals(failure?.message, mockMovieDetailFailureModel.message)
        }.collect()
    }
}
