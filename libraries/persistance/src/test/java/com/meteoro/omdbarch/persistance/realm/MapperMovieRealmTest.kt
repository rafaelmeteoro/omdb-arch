package com.meteoro.omdbarch.persistance.realm

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRealm
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class MapperMovieRealmTest {

    lateinit var mapper: MapperMovieRealm

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

    private val realm = FavoriteMovieRealm(
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
        mapper = MapperMovieRealm()
    }

    @Test
    fun `should mapper movie realm`() {
        val provided = domain
        val expected = realm

        assertThat(mapper.fromMovie(provided)).isEqualTo(expected)
    }

    @Test
    fun `should mapper movie`() {
        val provided = realm
        val expected = domain

        assertThat(mapper.fromRealm(provided)).isEqualTo(expected)
    }
}
