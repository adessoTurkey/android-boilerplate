package com.adesso.movee.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adesso.movee.data.remote.datasource.MovieRemoteDataSource
import com.adesso.movee.data.remote.model.movie.MovieDetailResponseModel
import com.adesso.movee.data.repository.mapper.MovieMapper
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.adesso.movee.scene.movielist.model.MovieGenreItemUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
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
class MovieRepositoryTest {

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

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repo: MovieRepository

    @MockK
    lateinit var remoteDataSource: MovieRemoteDataSource

    @MockK
    lateinit var mapper: MovieMapper

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        repo = MovieRepository(remoteDataSource, mapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when repo fetch method called should return flow response`() = runBlocking {
        val movieId = 100L

        // given
        coEvery { mapper.toUiModel(any() as MovieDetailResponseModel) } coAnswers {
            mockMovieDetailSuccessUIModel
        }

        coEvery { remoteDataSource.fetchMovieDetailFlow(any()) } coAnswers {
            flow { mockk() }
        }

        // then
        repo.fetchMovieDetailFlow(movieId).collect {
            assertEquals(it, mockMovieDetailSuccessUIModel)
        }
    }
}
