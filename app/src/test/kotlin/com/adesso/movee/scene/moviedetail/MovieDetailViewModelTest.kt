package com.adesso.movee.scene.moviedetail

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adesso.movee.domain.FetchMovieDetailFlowUseCase
import com.adesso.movee.internal.popup.PopupModel
import com.adesso.movee.internal.util.Event
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieGenreItemUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    private val mockMovieDetailSuccessUIModel = MovieDetailUiModel(
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

    private val apiErrorMessage = "Undefined Movie ID"
    private val mockMovieDetailFailureModel =
        Failure.ApiError(code = 201, message = apiErrorMessage)
    private val exceptedFailurePopupModel = PopupModel(message = apiErrorMessage)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailViewModel

    @MockK
    lateinit var useCase: FetchMovieDetailFlowUseCase

    @MockK
    lateinit var application: Application

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = MovieDetailViewModel(application, useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetch movie detail called should return movie detail ui model with kotlin flow`() =
        runBlocking {
            val movieId = 100L

            // given
            coEvery { useCase.execute(any()) } coAnswers {
                flow { emit(mockMovieDetailSuccessUIModel) }
            }

            // when
            viewModel.fetchMovieDetail(movieId)

            // then
            assertEquals(viewModel.movieDetail.value, mockMovieDetailSuccessUIModel)
        }

    @Test
    fun `when fetch movie detail called with wrong id should throw failure with kotlin flow`() =
        runBlocking {
            val movieId = -1L

            // given
            coEvery { useCase.execute(any()) } coAnswers {
                flow { throw mockMovieDetailFailureModel }
            }

            // when
            viewModel.fetchMovieDetail(movieId)

            // then
            assertEquals(viewModel.movieDetail.value, null)
            assertEquals(
                viewModel.failurePopup.value?.peekContent()?.message,
                Event(exceptedFailurePopupModel).peekContent().message
            )
        }
}
