package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.NetworkingError.*
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.networking.CheckErrorTransformation.Companion.checkTransformation
import org.assertj.core.api.Assertions.assertThat
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
            using = NetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(HostUnreachable) }
        )
    }

    @Test
    fun `should transform proper connect exception into host unreachable`() {
        checkTransformation(
            from = ConnectException(),
            using = NetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(HostUnreachable) }
        )
    }

    @Test
    fun `should transform proper no route exception into host unreachable`() {
        checkTransformation(
            from = NoRouteToHostException(),
            using = NetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(HostUnreachable) }
        )
    }

    @Test
    fun `should transform proper socket timeout exception into operation timeout`() {
        checkTransformation(
            from = SocketTimeoutException(),
            using = NetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(OperationTimeout) }
        )
    }

    @Test
    fun `should transform proper io exception into connection spike`() {
        checkTransformation(
            from = IOException("Canceled"),
            using = NetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(ConnectionSpike) }
        )
    }

    @Test
    fun `should propagate any other error`() {
        val otherError = RemoteServiceIntegrationError.RemoteSystem

        checkTransformation(
            from = otherError,
            using = NetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(otherError) }
        )
    }
}