package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.rest.util.DataRestUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class MapperMovieTest {

    lateinit var mapper: MapperMovie

    @Before
    fun `before each test`() {
        mapper = MapperMovie()
    }

    @Test
    fun `should map movie from response`() {
        val response = DataRestUtil.movieResponse
        val expected = DataRestUtil.movie

        assertThat(mapper.fromResponse(response)).isEqualTo(expected)
    }
}
