package com.meteoro.omdbarch.features.home

import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.utilities.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class HomeViewModelTests {

    lateinit var viewModel: HomeViewModel

    val mockFetch = mock<FetchSearch>()

    val result = ResultSearch(
        search = listOf(
            Movie(
                title = "Avengers",
                poster = "https://www.avenges/movie",
                imdbId = "idavenges"
            )
        ),
        response = "True",
        totalResults = 1
    )

    @Before
    fun `before each test`() {
        viewModel = HomeViewModel(
            fetch = mockFetch,
            machine = StateMachine()
        )
    }

    @Test
    fun `should emmit states for successful home presentation`() {
        val expected = BuildHomePresentation(result)

        whenever(mockFetch.searchMovies("Avengers"))
            .thenReturn(Observable.just(result))

        viewModel.searchMovie("Avengers").test()
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
        whenever(mockFetch.searchMovies("Avengers"))
            .thenReturn(Observable.error(NetworkingError.ConnectionSpike))

        viewModel.searchMovie("Avengers").test()
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