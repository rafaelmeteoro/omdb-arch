package com.meteoro.omdbarch.features.details

import com.meteoro.omdbarch.common.ResourceProvider
import com.meteoro.omdbarch.domain.FetchMovie
import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.utilities.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

class DetailViewModelTests {

    private lateinit var viewModel: DetailViewModel

    private val mockFetch = mock<FetchMovie>()
    private val mockProvider = mock<ResourceProvider>()

    private val result = Movie(
        title = "Avengers",
        year = "2012",
        imdbRating = "10",
        poster = "https://www.avengers/movie",
        director = "Russos",
        actors = "Chris",
        plot = "Movie"
    )

    @Before
    fun `before each test`() {
        viewModel = DetailViewModel(
            fetch = mockFetch,
            machine = StateMachine(),
            resProvider = mockProvider
        )
    }

    @Test
    fun `should emmit states for succesful detail presentation`() {
        whenever(mockProvider.getString(anyInt(), anyString()))
            .thenReturn("")

        val expected = BuildMovieDetailPresentation(result, mockProvider)

        whenever(mockFetch.fetchMovie(anyString()))
            .thenReturn(Observable.just(result))

        viewModel.fetchMovie("10").test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Result(expected),
                    Done
                )
            )
    }

    @Test
    fun `should emmit states for errored broking integration`() {
        whenever(mockFetch.fetchMovie(anyString()))
            .thenReturn(Observable.error(NetworkingError.ConnectionSpike))

        viewModel.fetchMovie("10").test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Failed(NetworkingError.ConnectionSpike),
                    Done
                )
            )
    }
}