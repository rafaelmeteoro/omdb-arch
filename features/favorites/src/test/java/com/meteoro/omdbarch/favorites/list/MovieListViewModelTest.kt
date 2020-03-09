package com.meteoro.omdbarch.favorites.list

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class MovieListViewModelTest {

    private lateinit var viewModel: MovieListViewModel

    private val mockCache = mock<CacheRepository>()

    @Before
    fun `before each test`() {
        viewModel = MovieListViewModel(
            cacheRepository = mockCache,
            machine = StateMachine()
        )
    }

    @Test
    fun `should emmit states for successful movies presentation`() {
        val movies = listOf(Movie(imdbId = "imdb", title = "Avengers"))

        val expected = MovieListPresentationMapper().fromDomain(movies)

        whenever(mockCache.getMovies())
            .thenReturn(Flowable.just(movies))

        viewModel.fetchMoviesSaved().test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    ViewState.Launched,
                    ViewState.Success(expected),
                    ViewState.Done
                )
            )

        verify(mockCache, times(timeInvocation())).getMovies()
        verify(mockCache, never()).deleteMovie(any())
        verify(mockCache, never()).deleteAll()
    }

    @Test
    fun `should emmit states for empty values`() {
        whenever(mockCache.getMovies())
            .thenReturn(Flowable.error(NoResultsFound))

        viewModel.fetchMoviesSaved().test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    ViewState.Launched,
                    ViewState.Failed(NoResultsFound),
                    ViewState.Done
                )
            )

        verify(mockCache, times(timeInvocation())).getMovies()
        verify(mockCache, never()).deleteMovie(any())
        verify(mockCache, never()).deleteAll()
    }

    @Test
    fun `should call delete movie when provided`() {
        viewModel.deleteMovie(Movie())

        verify(mockCache, times(timeInvocation())).deleteMovie(any())
        verify(mockCache, never()).getMovies()
        verify(mockCache, never()).deleteAll()
    }

    @Test
    fun `should call delete all`() {
        viewModel.deleteAll()

        verify(mockCache, times(timeInvocation())).deleteAll()
        verify(mockCache, never()).getMovies()
        verify(mockCache, never()).deleteMovie(any())
    }

    private fun timeInvocation() = 1
}