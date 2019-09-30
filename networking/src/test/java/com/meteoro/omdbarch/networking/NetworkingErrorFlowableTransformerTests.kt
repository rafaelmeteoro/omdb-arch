package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.NetworkingError.*
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.networking.CheckErrorFlowableTransformation.Companion.checkSingleTransformation
import org.junit.Test
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkingErrorFlowableTransformerTests {

    @Test
    fun `should transform proper unknown host exception into host unreachable`() {
        checkSingleTransformation(
            from = UnknownHostException("No Internet"),
            expected = HostUnreachable,
            using = NetworkingErrorFlowableTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper connect exception into host unreachable`() {
        checkSingleTransformation(
            from = ConnectException(),
            expected = HostUnreachable,
            using = NetworkingErrorFlowableTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper no route exception into host unreachable`() {
        checkSingleTransformation(
            from = NoRouteToHostException(),
            expected = HostUnreachable,
            using = NetworkingErrorFlowableTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper socket timeout exception into operation timeout`() {
        checkSingleTransformation(
            from = SocketTimeoutException(),
            expected = OperationTimeout,
            using = NetworkingErrorFlowableTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper io exception into connection spike`() {
        checkSingleTransformation(
            from = IOException("Canceled"),
            expected = ConnectionSpike,
            using = NetworkingErrorFlowableTransformer<Any>()
        )
    }

    @Test
    fun `should propagate any other error`() {
        val otherError = RemoteServiceIntegrationError.RemoteSystem

        checkSingleTransformation(
            from = otherError,
            expected = otherError,
            using = NetworkingErrorFlowableTransformer<Any>()
        )
    }
}