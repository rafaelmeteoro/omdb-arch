package com.meteoro.omdbarch.common

import com.meteoro.omdbarch.R
import com.meteoro.omdbarch.domain.errors.NetworkingError.HostUnreachable
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.ClientOrigin
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.RemoteSystem
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ErrorStateResourcesTests {

    @Test
    fun `should map resources for remote system error`() {
        val error = RemoteSystem
        val imageResource = R.drawable.img_server_down
        val messageResource = R.string.error_server_down

        val expected = ErrorStateResources(error)
        assertThat(expected.image).isEqualTo(imageResource)
        assertThat(expected.message).isEqualTo(messageResource)
    }

    @Test
    fun `should map resources for host unreachable error`() {
        val error = HostUnreachable
        val imageResource = R.drawable.img_network_issue
        val messageResource = R.string.error_network

        val expected = ErrorStateResources(error)
        assertThat(expected.image).isEqualTo(imageResource)
        assertThat(expected.message).isEqualTo(messageResource)
    }

    @Test
    fun `should map resources for no resutls found error`() {
        val error = NoResultsFound
        val imageResource = R.drawable.img_no_results
        val messageResource = R.string.error_no_results

        val expected = ErrorStateResources(error)
        assertThat(expected.image).isEqualTo(imageResource)
        assertThat(expected.message).isEqualTo(messageResource)
    }

    @Test
    fun `should map resources for empty term error`() {
        val error = EmptyTerm
        val imageResource = R.drawable.img_bug_found
        val messageResource = R.string.error_bug_found

        val expected = ErrorStateResources(error)
        assertThat(expected.image).isEqualTo(imageResource)
        assertThat(expected.message).isEqualTo(messageResource)
    }

    @Test
    fun `should map resources for client origin error`() {
        val error = ClientOrigin
        val imageResource = R.drawable.img_bug_found
        val messageResource = R.string.error_bug_found

        val expected = ErrorStateResources(error)
        assertThat(expected.image).isEqualTo(imageResource)
        assertThat(expected.message).isEqualTo(messageResource)
    }
}