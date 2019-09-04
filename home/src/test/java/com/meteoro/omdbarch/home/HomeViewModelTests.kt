package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.errors.SearchMoviesError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.utilities.StateMachine
import com.meteoro.omdbarch.utilities.ViewState.*
import com.nhaarman.mockitokotlin2.*
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
        val title = "Avengers"
        val timeInvocation = 1
        val expected = BuildHomePresentation(result)

        whenever(mockFetch.searchMovies(title))
            .thenReturn(Observable.just(result))

        viewModel.searchMovie(title).test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Success(expected),
                    Done
                )
            )

        verify(mockManager, times(timeInvocation)).save(title)
    }

    @Test
    fun `should emmit states for error result and never save search`() {
        val title = ""

        whenever(mockFetch.searchMovies(title))
            .thenReturn(Observable.error(SearchMoviesError.NoResultsFound))

        viewModel.searchMovie(title).test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Failed(SearchMoviesError.NoResultsFound),
                    Done
                )
            )

        verify(mockManager, never()).save(title)
    }

    @Test
    fun `should emmit states for errored broking integration`() {
        val title = "Avengers"
        val timeInvocation = 1

        whenever(mockFetch.searchMovies(title))
            .thenReturn(Observable.error(NetworkingError.ConnectionSpike))

        viewModel.searchMovie(title).test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Failed(NetworkingError.ConnectionSpike),
                    Done
                )
            )

        verify(mockManager, times(timeInvocation)).save(title)
    }
}