package com.meteoro.omdbarch.persistance

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom
import com.meteoro.omdbarch.persistance.room.MovieDao
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

internal class MovieCacheRoomInfrastructureTests {

    private val dao = mock<MovieDao>()
    lateinit var cache: MovieCacheRoomInfrastructure

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
        whenever(dao.favoriteMovie(anyString()))
            .thenReturn(Observable.just(favoriteMovie))

        cache.movieCached("").test()
            .assertComplete()
            .assertTerminated()
            .assertValue(movie)
    }

    @Test
    fun `should return a list movie`() {
        whenever(dao.allFavoritesMovies())
            .thenReturn(Observable.just(listOf(favoriteMovie)))

        cache.moviesCached().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(listOf(movie))
    }
}