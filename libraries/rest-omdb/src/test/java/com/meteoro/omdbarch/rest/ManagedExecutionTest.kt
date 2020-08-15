package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.rest.util.unwrapCaughtError
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class ManagedExecutionTest {

    @Test
    fun `should transform unknown host with managed execution`() {
        runBlocking {
            val result = runCatching { managedExecution { emulateError(UnknownHostException("No Internet")) } }
            val unwrapped = unwrapCaughtError(result)
            assertThat(unwrapped).isEqualTo(NetworkingError.HostUnreachable)
        }
    }

    @Test
    fun `should transform serialization exception with managed execution`() {
        runBlocking {
            val result = runCatching { managedExecution { emulateError(SerializationException("Ouch")) } }
            val unwrapped = unwrapCaughtError(result)
            assertThat(unwrapped).isEqualTo(RemoteServiceIntegrationError.UnexpectedResponse)
        }
    }

    @Test
    fun `should transform http exception with managed execution`() {
        runBlocking {
            val result = runCatching { managedExecution { emulateError(httpException()) } }
            val unwrapped = unwrapCaughtError(result)
            assertThat(unwrapped).isEqualTo(RemoteServiceIntegrationError.RemoteSystem)
        }
    }

    @Test
    fun `should propagate other with managed execution`() {
        val toBeProgated = IllegalStateException("Houston, we have a problem!")

        runBlocking {
            val result = runCatching { managedExecution { emulateError(toBeProgated) } }
            val unwrapped = unwrapCaughtError(result)
            assertThat(unwrapped).isEqualTo(toBeProgated)
        }
    }

    private suspend fun emulateError(error: Throwable): Unit =
        suspendCoroutine { continuation ->
            continuation.resumeWithException(error)
        }

    private fun httpException(): HttpException {
        val jsonMediaType = "application/json".toMediaTypeOrNull()
        val body = "Server down".toResponseBody(jsonMediaType)
        return HttpException(Response.error<Nothing>(503, body))
    }
}