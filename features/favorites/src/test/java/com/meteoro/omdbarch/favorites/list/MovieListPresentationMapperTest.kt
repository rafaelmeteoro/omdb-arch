package com.meteoro.omdbarch.favorites.list

import com.meteoro.omdbarch.domain.model.Movie
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class MovieListPresentationMapperTest {

    lateinit var mapper: MovieListPresentationMapper

    @Before
    fun `before each test`() {
        mapper = MovieListPresentationMapper()
    }

    @Test
    fun `should build movie list presentation`() {
        val movie = Movie(imdbId = "imdb", title = "Avengers")

        val provided = listOf(movie)

        val expected = MovieListPresentation(
            movies = listOf(movie)
        )

        assertThat(mapper.fromDomain(provided)).isEqualTo(expected)
    }
}