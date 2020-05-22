package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.model.TypeMovie
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class HomePresentationMapperTest {

    lateinit var mapper: HomePresentationMapper

    @Before
    fun `before each test`() {
        mapper = HomePresentationMapper()
    }

    @Test
    fun `should build presentation with type series`() {
        val provided = ResultSearch(
            search = listOf(
                Movie(
                    title = "Avengers",
                    poster = "https://www.avenges/movie",
                    imdbId = "idavenges",
                    type = "series"
                )
            ),
            response = "True",
            totalResults = 1
        )

        val expected = HomePresentation(
            movies = listOf(
                MoviePresentation(
                    title = "Avengers",
                    photoUrl = "https://www.avenges/movie",
                    imdbId = "idavenges",
                    type = TypeMovie.SERIES
                )
            ),
            response = "True",
            totalResults = 1
        )

        assertThat(mapper.fromDomain(provided)).isEqualTo(expected)
    }

    @Test
    fun `should build presentation with type movie`() {
        val provided = ResultSearch(
            search = listOf(
                Movie(
                    title = "Avengers",
                    poster = "https://www.avenges/movie",
                    imdbId = "idavenges",
                    type = "movie"
                )
            ),
            response = "True",
            totalResults = 1
        )

        val expected = HomePresentation(
            movies = listOf(
                MoviePresentation(
                    title = "Avengers",
                    photoUrl = "https://www.avenges/movie",
                    imdbId = "idavenges",
                    type = TypeMovie.MOVIE
                )
            ),
            response = "True",
            totalResults = 1
        )

        assertThat(mapper.fromDomain(provided)).isEqualTo(expected)
    }

    @Test
    fun `should build presentation with differ type`() {
        val provided = ResultSearch(
            search = listOf(
                Movie(
                    title = "Avengers",
                    poster = "https://www.avenges/movie",
                    imdbId = "idavenges",
                    type = "game"
                )
            ),
            response = "True",
            totalResults = 1
        )

        val expected = HomePresentation(
            movies = listOf(
                MoviePresentation(
                    title = "Avengers",
                    photoUrl = "https://www.avenges/movie",
                    imdbId = "idavenges",
                    type = TypeMovie.OUTRO
                )
            ),
            response = "True",
            totalResults = 1
        )

        assertThat(mapper.fromDomain(provided)).isEqualTo(expected)
    }

    @Test
    fun `should build presentation with null type`() {
        val provided = ResultSearch(
            search = listOf(
                Movie(
                    title = "Avengers",
                    poster = "https://www.avenges/movie",
                    imdbId = "idavenges",
                    type = null
                )
            ),
            response = "True",
            totalResults = 1
        )

        val expected = HomePresentation(
            movies = listOf(
                MoviePresentation(
                    title = "Avengers",
                    photoUrl = "https://www.avenges/movie",
                    imdbId = "idavenges",
                    type = TypeMovie.OUTRO
                )
            ),
            response = "True",
            totalResults = 1
        )

        assertThat(mapper.fromDomain(provided)).isEqualTo(expected)
    }
}
