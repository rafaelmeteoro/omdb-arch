package com.meteoro.omdbarch.persistance.room

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BuildMovieRoomTests {

    @Test
    fun `should build movie room`() {
        val provided = Movie(
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

        val expected = FavoriteMovieRoom(
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

        assertThat(BuildMovieRoom(provided)).isEqualTo(expected)
    }
}