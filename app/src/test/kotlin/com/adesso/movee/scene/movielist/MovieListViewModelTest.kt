package com.adesso.movee.scene.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adesso.movee.domain.FetchNowPlayingMoviesUseCase
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.movielist.model.MovieUiModel
import com.github.michaelbull.result.Ok
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val fetchNowPlayingMoviesUseCase = mockk<FetchNowPlayingMoviesUseCase>()
    private lateinit var viewModel: MovieListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieListViewModel(fetchNowPlayingMoviesUseCase)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchNowPlayingMovies should post now playing movies`() {
        val expected = listOf(
            MovieUiModel(
                id = 1,
                title = "",
                overview = "",
                posterPath = null,
                average = 0.0,
                releaseDate = null,
                backdropPath = null,
                popularity = 0.0,
                isAdult = false
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
            ),
        )

        every { fetchNowPlayingMoviesUseCase(any(), any(), any()) } answers {
            val continuation =
                (args[2] as ((com.github.michaelbull.result.Result<List<MovieUiModel>, Failure>) -> Unit))
            continuation(Ok(expected))
        }

        viewModel.fetchNowPlayingMovies()

        assert(viewModel.nowPlayingMovies.value == expected)
    }
}
