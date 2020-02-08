package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.domain.model.Movie
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BuildMovieDetailsPresentationTest {

    @Test
    fun `should build movie detail presentation`() {
        val provided = Movie(imdbId = "imdb", title = "Avengers")

        val expected = MovieDetailsPresentation(
            movie = provided
        )

        assertThat(BuildMovieDetailsPresentation(provided)).isEqualTo(expected)
    }
}