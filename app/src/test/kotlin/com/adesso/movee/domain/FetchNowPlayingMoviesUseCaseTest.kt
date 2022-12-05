package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.movielist.model.MovieUiModel
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class FetchNowPlayingMoviesUseCaseTest {

    @MockK
    lateinit var repository: MovieRepository

    @InjectMockKs
    lateinit var fetchNowPlayingMoviesUseCase: FetchNowPlayingMoviesUseCase

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun `fetch data successfully from repository when fetch now playing movies data`() {
        val mockedResponse: List<MovieUiModel> = mockk()

        coEvery {
            repository.fetchNowPlayingMovies()
        } returns mockedResponse

        val result: Result<List<MovieUiModel>, Failure> =
            runBlocking {
                fetchNowPlayingMoviesUseCase.run(
                    mockk(relaxed = true)
                )
            }

        assertNotNull(result.get())
        assertEquals(mockedResponse, result.get())
    }

    @Test
    fun `fetch data unsuccessfully from repository when fetch now playing movies fails`() {
        val errorResponse = Failure.EmptyResponse

        coEvery {
            repository.fetchNowPlayingMovies()
        } throws errorResponse

        val result =
            runBlocking {
                fetchNowPlayingMoviesUseCase.run(
                    mockk(relaxed = true)
                )
            }

        assertNotNull(result.getError())
        assertEquals(errorResponse, result.getError())
    }
}
