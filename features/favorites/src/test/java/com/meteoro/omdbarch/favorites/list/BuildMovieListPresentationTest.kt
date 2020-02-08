package com.meteoro.omdbarch.favorites.list

import com.meteoro.omdbarch.domain.model.Movie
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BuildMovieListPresentationTest {

    @Test
    fun `should build movie list presentation`() {
        val movie = Movie(imdbId = "imdb", title = "Avengers")

        val provided = listOf(movie)

        val expected = MovieListPresentation(
            movies = listOf(movie)
        )

        assertThat(BuildMovieListPresentation(provided)).isEqualTo(expected)
    }
}