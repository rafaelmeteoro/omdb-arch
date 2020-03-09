package com.meteoro.omdbarch.persistance

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom
import com.meteoro.omdbarch.persistance.room.MovieDao
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

internal class MovieCacheRoomInfrastructureTest {

    private val dao = mock<MovieDao>()
    lateinit var cache: MovieCacheService

    private val movie by lazy {
        Movie(
            imdbId = "imdb",
            title = "Avengers"
        )
    }

    private val favoriteMovie by lazy {
        FavoriteMovieRoom(
            imdbId = "imdb",
            title = "Avengers"
        )
    }

    @Before
    fun `before each test`() {
        cache = MovieCacheRoomInfrastructure(dao)
    }

    @Test
    fun `should return a movie`() {
        val imdb = "imdb"

        whenever(dao.favoriteMovie(anyString()))
            .thenReturn(Maybe.just(favoriteMovie))

        cache.movieCached(imdb).test()
            .assertComplete()
            .assertTerminated()
            .assertValue(movie)

        verify(dao, times(timeInvocation())).favoriteMovie(imdb)
        verify(dao, never()).insert(any())
        verify(dao, never()).remove(any())
        verify(dao, never()).clear()
        verify(dao, never()).allFavoritesMovies()
    }

    @Test
    fun `should return a list movie`() {
        whenever(dao.allFavoritesMovies())
            .thenReturn(Maybe.just(listOf(favoriteMovie)))

        cache.moviesCached().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(listOf(movie))

        verify(dao, times(timeInvocation())).allFavoritesMovies()
        verify(dao, never()).insert(any())
        verify(dao, never()).remove(any())
        verify(dao, never()).clear()
        verify(dao, never()).favoriteMovie(anyString())
    }

    @Test
    fun `should save movie when provided`() {
        cache.save(movie)

        verify(dao, times(timeInvocation())).insert(any())
        verify(dao, never()).remove(any())
        verify(dao, never()).clear()
        verify(dao, never()).favoriteMovie(anyString())
        verify(dao, never()).allFavoritesMovies()
    }

    @Test
    fun `should delete movie when provided`() {
        cache.delete(movie)

        verify(dao, times(timeInvocation())).remove(any())
        verify(dao, never()).insert(any())
        verify(dao, never()).clear()
        verify(dao, never()).favoriteMovie(anyString())
        verify(dao, never()).allFavoritesMovies()
    }

    @Test
    fun `should clear all movies`() {
        cache.deleteAll()

        verify(dao, times(timeInvocation())).clear()
        verify(dao, never()).insert(any())
        verify(dao, never()).remove(any())
        verify(dao, never()).favoriteMovie(anyString())
        verify(dao, never()).allFavoritesMovies()
    }

    private fun timeInvocation() = 1
}