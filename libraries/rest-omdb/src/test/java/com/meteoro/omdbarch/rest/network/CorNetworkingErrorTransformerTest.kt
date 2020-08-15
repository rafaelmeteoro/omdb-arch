package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.rest.network.CorCheckErrorTransformation.Companion.corCheckTransformation
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class CorNetworkingErrorTransformerTest {

    @Test
    fun `should transform proper unknown host exception into host unreachable`() {
        corCheckTransformation(
            from = UnknownHostException("No Internet"),
            using = CorNetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(NetworkingError.HostUnreachable) }
        )
    }

    @Test
    fun `should transform proper connect exception into host unreachable`() {
        corCheckTransformation(
            from = ConnectException(),
            using = CorNetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(NetworkingError.HostUnreachable) }
        )
    }

    @Test
    fun `should transform proper not route exception into host unreachable`() {
        corCheckTransformation(
            from = NoRouteToHostException(),
            using = CorNetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(NetworkingError.HostUnreachable) }
        )
    }

    @Test
    fun `should transform proper socket timeout exception into operation timeout`() {
        corCheckTransformation(
            from = SocketTimeoutException(),
            using = CorNetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(NetworkingError.OperationTimeout) }
        )
    }

    @Test
    fun `should transform proper io exception into connection spike`() {
        corCheckTransformation(
            from = IOException("Canceled"),
            using = CorNetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(NetworkingError.ConnectionSpike) }
        )
    }

    @Test
    fun `should propagate any other error`() {
        val otherError = RemoteServiceIntegrationError.RemoteSystem

        corCheckTransformation(
            from = otherError,
            using = CorNetworkingErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(otherError) }
        )
    }
}