package com.meteoro.omdbarch.domain.errors

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NoConnectivityErrorTest {

    @Test
    fun `should be no connectivity error`() {
        val error = NoConnectivityError
        val message = "No internet connection"

        assertThat(error.toString()).isEqualTo(message)
    }
}