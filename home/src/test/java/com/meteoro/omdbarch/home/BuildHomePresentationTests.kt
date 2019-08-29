package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BuildHomePresentationTests {

    @Test
    fun `should build presentation`() {
        val provided = ResultSearch(
            search = listOf(
                Movie(
                    title = "Avengers",
                    poster = "https://www.avenges/movie",
                    imdbId = "idavenges"
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
                    imdbId = "idavenges"
                )
            ),
            response = "True",
            totalResults = 1
        )

        assertThat(BuildHomePresentation(provided)).isEqualTo(expected)
    }
}