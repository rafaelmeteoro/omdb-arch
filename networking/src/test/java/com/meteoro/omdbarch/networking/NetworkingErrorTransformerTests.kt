package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.NetworkingError.*
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.networking.CheckErrorTransformation.Companion.checkTransformation
import org.junit.Test
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkingErrorTransformerTests {

    @Test
    fun `should transform proper unknown host exception into host unreachable`() {
        checkTransformation(
            from = UnknownHostException("No Internet"),
            expected = HostUnreachable,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper connect exception into host unreachable`() {
        checkTransformation(
            from = ConnectException(),
            expected = HostUnreachable,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper no route exception into host unreachable`() {
        checkTransformation(
            from = NoRouteToHostException(),
            expected = HostUnreachable,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper socket timeout exception into operation timeout`() {
        checkTransformation(
            from = SocketTimeoutException(),
            expected = OperationTimeout,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper io exception into connection spike`() {
        checkTransformation(
            from = IOException("Canceled"),
            expected = ConnectionSpike,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should propagate any other error`() {
        val otherError = RemoteServiceIntegrationError.RemoteSystem

        checkTransformation(
            from = otherError,
            expected = otherError,
            using = NetworkingErrorTransformer<Any>()
        )
    }
}