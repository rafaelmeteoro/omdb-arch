package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.components.StateMachine
import com.meteoro.omdbarch.components.ViewState
import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.domain.errors.SearchMoviesError
import com.meteoro.omdbarch.domain.model.Movie
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class MovieDetailsViewModelTest {

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
        val movie = Movie(imdbId = imdb, title = "Avengers")
        val expected = BuildMovieDetailsPresentation(movie)

        whenever(mockCache.getMovie(anyString()))
            .thenReturn(Flowable.just(movie))

        viewModel.fetchMovieSaved(imdb).test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    ViewState.Launched,
                    ViewState.Success(expected),
                    ViewState.Done
                )
            )

        verify(mockCache, times(timeInvocation())).getMovie(imdb)
    }

    @Test
    fun `should emmit states for empty values`() {
        val imdb = "imdb"

        whenever(mockCache.getMovie(anyString()))
            .thenReturn(Flowable.error(SearchMoviesError.NoResultsFound))

        viewModel.fetchMovieSaved(imdb).test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    ViewState.Launched,
                    ViewState.Failed(SearchMoviesError.NoResultsFound),
                    ViewState.Done
                )
            )

        verify(mockCache, times(timeInvocation())).getMovie(imdb)
    }

    private fun timeInvocation() = 1
}