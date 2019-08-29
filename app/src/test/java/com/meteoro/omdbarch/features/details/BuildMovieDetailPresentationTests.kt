package com.meteoro.omdbarch.features.details

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.utilities.ResourceProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

class BuildMovieDetailPresentationTests {

    private val resMock = mock<ResourceProvider>()
    private val resTest = "teste"

    @Test
    fun `should build presentation`() {
        whenever(resMock.getString(anyInt(), anyString()))
            .thenReturn(resTest)

        val provided = Movie(
            title = "Avengers",
            year = "2012",
            imdbRating = "10",
            actors = "Chris",
            director = "Brother Russos",
            plot = "Grande filme",
            poster = "http://www.avengers/movie"
        )

        val expected = MovieDetailPresentation(
            title = "Avengers",
            year = "2012",
            rating = "teste",
            cast = "teste",
            directors = "teste",
            plot = "teste",
            poster = "http://www.avengers/movie"
        )

        assertThat(BuildMovieDetailPresentation(provided, resMock)).isEqualTo(expected)
    }
}