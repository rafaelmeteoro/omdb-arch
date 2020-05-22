package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.rest.response.MovieResponse
import com.meteoro.omdbarch.rest.response.SearchResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class MapperResultSearchTest {

    lateinit var mapper: MapperResultSearch

    @Before
    fun `before each test`() {
        mapper = MapperResultSearch()
    }

    @Test
    fun `should map search from response`() {
        val response = SearchResponse(
            search = listOf(
                MovieResponse(
                    title = "Avengers: Infinity War",
                    year = "2018",
                    imdbId = "tt4154756",
                    type = "movie",
                    poster = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_SX300.jpg"
                ),
                MovieResponse(
                    title = "Avengers: Endgame",
                    year = "2019",
                    imdbId = "tt4154796",
                    type = "movie",
                    poster = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_SX300.jpg"
                )
            ),
            totalResult = 94,
            response = "True"
        )

        val expected = ResultSearch(
            search = listOf(
                Movie(
                    title = "Avengers: Infinity War",
                    year = "2018",
                    imdbId = "tt4154756",
                    type = "movie",
                    poster = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_SX1200.jpg"
                ),
                Movie(
                    title = "Avengers: Endgame",
                    year = "2019",
                    imdbId = "tt4154796",
                    type = "movie",
                    poster = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_SX1200.jpg"
                )
            ),
            totalResults = 94,
            response = "True"
        )

        assertThat(mapper.fromResponse(response)).isEqualTo(expected)
    }
}
