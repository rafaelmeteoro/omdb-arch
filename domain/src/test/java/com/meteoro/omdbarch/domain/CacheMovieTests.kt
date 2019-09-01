package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.domain.util.movie
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class CacheMovieTests {

    private val service = mock<MovieCacheService>()
    private lateinit var cache: CacheMovie

    @Before
    fun `before each test`() {
        cache = CacheMovie(service)
    }

    @Test
    fun `should return a movie`() {
        whenever(service.movieCached(anyString()))
            .thenReturn(Observable.just(movie))

        cache.getMovie("").test()
            .assertComplete()
            .assertTerminated()
            .assertValue(movie)
    }

    @Test
    fun `should return a list movie`() {
        val movies = listOf(movie)

        whenever(service.moviesCached())
            .thenReturn(Observable.just(movies))

        cache.getMovies().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(movies)
    }

    @Test
    fun `should return error when no has movie cached`() {
        whenever(service.moviesCached())
            .thenReturn(Observable.just(emptyList()))

        cache.getMovies().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)
    }
}