package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.domain.model.Movie
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class MovieDetailsPresentationMapperTest {

    lateinit var mapper: MovieDetailsPresentationMapper

    @Before
    fun `before each test`() {
        mapper = MovieDetailsPresentationMapper()
    }

    @Test
    fun `should build movie details presentation`() {
        val provided = Movie(imdbId = "imdb", title = "Avengers")

        val expected = MovieDetailsPresentation(
            movie = provided
        )

        assertThat(mapper.fromDomain(provided)).isEqualTo(expected)
    }
}
