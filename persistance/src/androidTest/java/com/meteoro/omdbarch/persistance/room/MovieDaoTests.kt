package com.meteoro.omdbarch.persistance.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTests {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: OmdbRoomDatabase

    @Before
    fun initDb() {
        // using a in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), OmdbRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getMovieWhenNoInserted() {
        database.movieDao().favoriteMovie(MOVIE.imdbId)
            .test()
            .assertNoValues()
    }

    @Test
    fun getMoviesListWhenNoInserted() {
        database.movieDao().allFavoritesMovies()
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun insertAndGetMovie() {
        // When inserting a new movie in the data source
        database.movieDao().insert(MOVIE)

        // When subscribing to the emissions of the movie
        database.movieDao().favoriteMovie(MOVIE.imdbId)
            .test()
            .assertValue { it.imdbId == MOVIE.imdbId && it.title == MOVIE.title }
    }

    @Test
    fun insertAndGetListMovie() {
        // When inserting a new movie in the data source
        val movie1 = FavoriteMovieRoom(imdbId = "id1", title = "movie1")
        val movie2 = FavoriteMovieRoom(imdbId = "id2", title = "movie2")

        database.movieDao().insert(movie1)
        database.movieDao().insert(movie2)

        // When subscribing to the emissions of the movie
        database.movieDao().allFavoritesMovies()
            .test()
            .assertValue { it.size == 2 }
    }

    @Test
    fun deleteAndGetMovie() {
        // Given that we have a user in the data source
        database.movieDao().insert(MOVIE)

        // check movie inserted
        database.movieDao().favoriteMovie(MOVIE.imdbId)
            .test()
            .assertValue { it.imdbId == MOVIE.imdbId && it.title == MOVIE.title }

        // When we are remove movie
        database.movieDao().remove(MOVIE)
        // When subscribing to the emissions of the movie
        database.movieDao().favoriteMovie(MOVIE.imdbId)
            .test()
            .assertNoValues()
    }

    @Test
    fun deleteAllAndGetMovies() {
        // Given that we have a user in the data source
        database.movieDao().insert(MOVIE)

        // check movie inserted
        database.movieDao().favoriteMovie(MOVIE.imdbId)
            .test()
            .assertValue { it.imdbId == MOVIE.imdbId && it.title == MOVIE.title }

        // When we are deleting all movies
        database.movieDao().clear()
        // When subscribing the emissions of the movie
        database.movieDao().allFavoritesMovies()
            .test()
            .assertValue { it.isEmpty() }
    }

    companion object {
        private val MOVIE = FavoriteMovieRoom(imdbId = "id", title = "Avengers")
    }
}