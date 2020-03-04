package com.meteoro.omdbarch.persistance.room

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class MapperMovieRoomTest {

    lateinit var mapper: MapperMovieRoom

    private val domain = Movie(
        imdbId = "tt2474438",
        title = "Avengers",
        year = "2013",
        released = "30 Oct 2013",
        runtime = "120 min",
        genre = "movie",
        director = "alguem",
        actors = "eu e eu mesmo",
        plot = "plot",
        country = "usa",
        poster = "images",
        imdbRating = "9.0",
        imdbVotes = "2,0000",
        type = "movie"
    )

    private val room = FavoriteMovieRoom(
        imdbId = "tt2474438",
        title = "Avengers",
        year = "2013",
        released = "30 Oct 2013",
        runtime = "120 min",
        genre = "movie",
        director = "alguem",
        actors = "eu e eu mesmo",
        plot = "plot",
        country = "usa",
        poster = "images",
        imdbRating = "9.0",
        imdbVotes = "2,0000",
        type = "movie"
    )

    @Before
    fun `before each test`() {
        mapper = MapperMovieRoom()
    }

    @Test
    fun `should mapper movie room`() {
        val provided = domain
        val expected = room

        assertThat(mapper.fromMovie(provided)).isEqualTo(expected)
    }

    @Test
    fun `should mapper movie`() {
        val provided = room
        val expected = domain

        assertThat(mapper.fromRoom(provided)).isEqualTo(expected)
    }
}