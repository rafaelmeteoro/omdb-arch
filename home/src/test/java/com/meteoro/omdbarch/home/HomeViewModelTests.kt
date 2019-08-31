package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.utilities.StateMachine
import com.meteoro.omdbarch.utilities.ViewState.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class HomeViewModelTests {

    private lateinit var viewModel: HomeViewModel

    private val mockFetch = mock<FetchSearch>()
    private val mockManager = mock<ManagerSearch>()

    private val result = ResultSearch(
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
            manager = mockManager,
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
                    Success(expected),
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