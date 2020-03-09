package com.meteoro.omdbarch.details

import com.meteoro.omdbarch.components.ResourceProvider
import com.meteoro.omdbarch.domain.model.Movie
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

class MovieDetailPresentationMapperTest {

    private val resMock = mock<ResourceProvider>()
    lateinit var mapper: MovieDetailPresentationMapper

    private val resTest = "teste"

    @Before
    fun `before each test`() {
        mapper = MovieDetailPresentationMapper()
    }

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

        assertThat(mapper.fromDomain(provided, resMock)).isEqualTo(expected)
    }
}