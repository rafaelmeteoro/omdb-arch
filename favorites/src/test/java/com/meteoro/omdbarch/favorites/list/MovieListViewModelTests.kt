package com.meteoro.omdbarch.favorites.list

import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.utilities.StateMachine
import com.meteoro.omdbarch.utilities.ViewState.*
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class MovieListViewModelTests {

    private lateinit var viewModel: MovieListViewModel

    private val mockCache = mock<CacheMovie>()

    @Before
    fun `before each test`() {
        viewModel = MovieListViewModel(
            cache = mockCache,
            machine = StateMachine()
        )
    }

    @Test
    fun `should emmit states for successful movies presentation`() {
        val timeInvocation = 1

        val movies = listOf(Movie(imdbId = "imdb", title = "Avengers"))

        val expected = BuildMovieListPresentation(movies)

        whenever(mockCache.getMovies())
            .thenReturn(Observable.just(movies))

        viewModel.fetchMoviesSaved().test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Success(expected),
                    Done
                )
            )

        verify(mockCache, times(timeInvocation)).getMovies()
        verify(mockCache, never()).deleteMovie(any())
    }

    @Test
    fun `should emmit states for empty values`() {
        val timeInvocation = 1

        whenever(mockCache.getMovies())
            .thenReturn(Observable.error(NoResultsFound))

        viewModel.fetchMoviesSaved().test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Failed(NoResultsFound),
                    Done
                )
            )

        verify(mockCache, times(timeInvocation)).getMovies()
        verify(mockCache, never()).deleteMovie(any())
    }

    @Test
    fun `should call delete movie when provided`() {
        val timeInvocation = 1

        viewModel.deleteMovie(Movie())

        verify(mockCache, times(timeInvocation)).deleteMovie(any())
        verify(mockCache, never()).getMovies()
    }
}