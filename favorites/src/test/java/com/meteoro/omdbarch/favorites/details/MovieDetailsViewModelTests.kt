package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.domain.errors.SearchMoviesError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.utilities.StateMachine
import com.meteoro.omdbarch.utilities.ViewState.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class MovieDetailsViewModelTests {

    private lateinit var viewModel: MovieDetailsViewModel

    private val mockCache = mock<CacheMovie>()

    @Before
    fun `before each test`() {
        viewModel = MovieDetailsViewModel(
            cache = mockCache,
            machine = StateMachine()
        )
    }

    @Test
    fun `should emmit states for successful movie presentation`() {
        val imdb = "imdb"
        val timeInvocation = 1

        val movie = Movie(imdbId = imdb, title = "Avengers")

        val expected = BuildMovieDetailsPresentation(movie)

        whenever(mockCache.getMovie(anyString()))
            .thenReturn(Observable.just(movie))

        viewModel.fetchMovieSaved(imdb).test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Success(expected),
                    Done
                )
            )

        verify(mockCache, times(timeInvocation)).getMovie(imdb)
    }

    @Test
    fun `should emmit states for empty values`() {
        val imdb = "imdb"
        val timeInvocation = 1

        whenever(mockCache.getMovie(anyString()))
            .thenReturn(Observable.error(SearchMoviesError.NoResultsFound))

        viewModel.fetchMovieSaved(imdb).test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Failed(SearchMoviesError.NoResultsFound),
                    Done
                )
            )

        verify(mockCache, times(timeInvocation)).getMovie(imdb)
    }
}