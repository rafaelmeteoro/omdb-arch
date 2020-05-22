package com.meteoro.omdbarch.domain.errors

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class NetworkingErrorTest {

    @Test
    fun `should be host unreachable`() {
        val error: NetworkingError = NetworkingError.HostUnreachable
        val message = "Cannot reach remote host"

        assertThat(error.toString()).isEqualTo(message)
    }

    @Test
    fun `should be operation timeout`() {
        val error: NetworkingError = NetworkingError.OperationTimeout
        val message = "Networking operation timed out"

        assertThat(error.toString()).isEqualTo(message)
    }

    @Test
    fun `should be connectionSpike`() {
        val error: NetworkingError = NetworkingError.ConnectionSpike
        val message = "In-flight networking operation interrupted"

        assertThat(error.toString()).isEqualTo(message)
    }

    @Test
    fun `should be no internet connection`() {
        val error: NetworkingError = NetworkingError.NoInternetConnection
        val message = "No Internet Connection"

        assertThat(error.toString()).isEqualTo(message)
    }
}
