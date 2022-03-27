package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchMovieDetailUseCaseTest {

    @MockK
    lateinit var repository: MovieRepository

    @InjectMockKs
    lateinit var fetchMovieDetailUseCase: FetchMovieDetailUseCase

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun `fetch data successfully from repository when fetch movie detail data`() {
        val mockedResponse: MovieDetailUiModel = mockk()

        val idSlot = slot<Long>()
        coEvery {
            repository.fetchMovieDetail(
                id = capture(idSlot)
            )
        } returns mockedResponse

        val result =
            runBlocking {
                fetchMovieDetailUseCase.run(
                    FetchMovieDetailUseCase.Params(
                        id = 1
                    )
                )
            }

        assertTrue(result.isRight)
        result.ifRight {
            assertEquals(mockedResponse, it)
        }

        assertEquals(
            "id of the movie",
            1,
            idSlot.captured
        )
    }

    @Test
    fun `fetch data unsuccessfully from repository when fetch movie detail fails`() {
        val errorResponse = Failure.EmptyResponse

        coEvery {
            repository.fetchMovieDetail(
                any()
            )
        } throws errorResponse

        val result =
            runBlocking {
                fetchMovieDetailUseCase.run(
                    mockk(relaxed = true)
                )
            }

        assertTrue(result.isLeft)
        result.ifLeft {
            assertEquals(errorResponse, it)
        }
    }
}
