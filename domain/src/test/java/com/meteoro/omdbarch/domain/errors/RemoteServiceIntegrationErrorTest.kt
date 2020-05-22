package com.meteoro.omdbarch.domain.errors

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class RemoteServiceIntegrationErrorTest {

    @Test
    fun `should be client origin`() {
        val error: RemoteServiceIntegrationError = RemoteServiceIntegrationError.ClientOrigin
        val message = "Issue originated from client"

        assertThat(error.toString()).isEqualTo(message)
    }

    @Test
    fun `should be remote system`() {
        val error: RemoteServiceIntegrationError = RemoteServiceIntegrationError.RemoteSystem
        val message = "Issue incoming from server"

        assertThat(error.toString()).isEqualTo(message)
    }

    @Test
    fun `should be unexpected response`() {
        val error: RemoteServiceIntegrationError = RemoteServiceIntegrationError.UnexpectedResponse
        val message = "Broken contract"

        assertThat(error.toString()).isEqualTo(message)
    }
}
