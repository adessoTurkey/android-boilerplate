package com.adesso.movee.scene.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adesso.movee.domain.FetchMovieDetailFlowUseCase
import com.adesso.movee.internal.popup.PopupModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieGenreItemUiModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

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
        Failure.NetworkError(message = apiErrorMessage)
    private val exceptedFailurePopupModel = PopupModel(message = apiErrorMessage)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailViewModel

    @MockK
    lateinit var useCase: FetchMovieDetailFlowUseCase

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = MovieDetailViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}
