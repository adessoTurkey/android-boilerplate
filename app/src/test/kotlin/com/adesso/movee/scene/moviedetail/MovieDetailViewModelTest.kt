package com.adesso.movee.scene.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adesso.movee.domain.FetchMovieDetailUseCase
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieGenreItemUiModel
import com.github.michaelbull.result.Ok
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @MockK
    lateinit var fetchMovieDetailUseCase: FetchMovieDetailUseCase

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        movieDetailViewModel = MovieDetailViewModel(fetchMovieDetailUseCase)
    }

    @Test
    fun `fetch movie detail should update movie detail flow`() = runTest {
        // Given
        val id = 1L
        val movieDetailUiModel = MovieDetailUiModel(
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
        every { fetchMovieDetailUseCase(any(), any(), any()) } answers {
            val continuation =
                (args[2] as ((com.github.michaelbull.result.Result<MovieDetailUiModel, Failure>) -> Unit))
            continuation(Ok(movieDetailUiModel))
        }
        // When
        movieDetailViewModel.fetchMovieDetail(id)

        // Then
        assert(movieDetailViewModel.movieDetailFlow.value == movieDetailUiModel)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}
