package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.domain.util.movie
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class CacheMovieTest {

    private val service = mock<MovieCacheService>()
    private lateinit var cache: CacheMovie

    @Before
    fun `before each test`() {
        cache = CacheMovie(service)
    }

    @Test
    fun `should return a movie`() {
        whenever(service.movieCached(anyString()))
            .thenReturn(Flowable.just(movie))

        cache.getMovie("").test()
            .assertComplete()
            .assertTerminated()
            .assertValue(movie)

        verify(service, never()).save(movie)
        verify(service, never()).delete(movie)
        verify(service, never()).deleteAll()
        verify(service, never()).moviesCached()
    }

    @Test
    fun `should return a list movie`() {
        val movies = listOf(movie)

        whenever(service.moviesCached())
            .thenReturn(Flowable.just(movies))

        cache.getMovies().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(movies)

        verify(service, never()).save(movie)
        verify(service, never()).delete(movie)
        verify(service, never()).deleteAll()
        verify(service, never()).movieCached(anyString())
    }

    @Test
    fun `should return error when no has movie cached`() {
        whenever(service.moviesCached())
            .thenReturn(Flowable.just(emptyList()))

        cache.getMovies().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)

        verify(service, never()).save(movie)
        verify(service, never()).delete(movie)
        verify(service, never()).deleteAll()
        verify(service, never()).movieCached(anyString())
    }

    @Test
    fun `shoud call save when movie is provided`() {
        val timeInvocation = 1

        cache.saveMovie(movie)

        verify(service, times(timeInvocation)).save(movie)
        verify(service, never()).delete(movie)
        verify(service, never()).deleteAll()
        verify(service, never()).movieCached(anyString())
        verify(service, never()).moviesCached()
    }

    @Test
    fun `should call delete when movie is provided`() {
        val timeInvocation = 1

        cache.deleteMovie(movie)

        verify(service, times(timeInvocation)).delete(movie)
        verify(service, never()).save(movie)
        verify(service, never()).deleteAll()
        verify(service, never()).movieCached(anyString())
        verify(service, never()).moviesCached()
    }

    @Test
    fun `should call delete all`() {
        val timeInvocation = 1

        cache.deleteAll()

        verify(service, times(timeInvocation)).deleteAll()
        verify(service, never()).save(movie)
        verify(service, never()).delete(movie)
        verify(service, never()).movieCached(anyString())
        verify(service, never()).moviesCached()
    }
}