package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.rest.network.CheckErrorTransformation.Companion.checkTransformation
import org.junit.Test
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class NetworkingErrorTransformerTest {

    @Test
    fun `should transform proper unknown host exception into host unreachable`() {
        checkTransformation(
            from = UnknownHostException("No Internet"),
            expected = NetworkingError.HostUnreachable,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper connect exception into host unreachable`() {
        checkTransformation(
            from = ConnectException(),
            expected = NetworkingError.HostUnreachable,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper no route exception into host unreachable`() {
        checkTransformation(
            from = NoRouteToHostException(),
            expected = NetworkingError.HostUnreachable,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper socket timeout exception into operation timeout`() {
        checkTransformation(
            from = SocketTimeoutException(),
            expected = NetworkingError.OperationTimeout,
            using = NetworkingErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper io exception into connection spike`() {
        checkTransformation(
            from = IOException("Canceled"),
            expected = NetworkingError.ConnectionSpike,
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
