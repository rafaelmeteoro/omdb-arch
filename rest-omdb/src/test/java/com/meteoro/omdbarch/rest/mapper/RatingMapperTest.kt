package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.rest.response.RatingResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RatingMapperTest {

    @Test
    fun `should map rating from response`() {
        val response = RatingResponse(
            source = "source",
            value = "value"
        )

        val expected = Rating(
            source = "source",
            value = "value"
        )

        assertThat(RatingMapper(response)).isEqualTo(expected)
    }
}