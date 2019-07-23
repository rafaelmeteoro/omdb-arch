package com.meteoro.omdbarch.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RatingTest {

    @Test
    fun testValuesRating() {
        val sourceExpected = "source"
        val valueExpected = "value"

        val rating = Rating(sourceExpected, valueExpected)

        assertThat(rating.source).isEqualTo(sourceExpected)
        assertThat(rating.value).isEqualTo(valueExpected)
    }
}