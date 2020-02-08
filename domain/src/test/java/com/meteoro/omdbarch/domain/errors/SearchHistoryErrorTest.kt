package com.meteoro.omdbarch.domain.errors

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SearchHistoryErrorTest {

    @Test
    fun `should be search history error`() {
        val error = SearchHistoryError
        val message = "Error when accessing local search"

        assertThat(error.toString()).isEqualTo(message)
    }
}