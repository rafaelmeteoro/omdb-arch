package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.NetworkingError
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkingErrorTransformer<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.onErrorResumeNext(this::handleIfNetworkingError)
    }

    private fun handleIfNetworkingError(error: Throwable) =
        if (isNetworkingError(error)) asNetworkingError(error)
        else Flowable.error(error)

    private fun asNetworkingError(error: Throwable) = Flowable.error<T>(
        mapToDomainError(error)
    )

    private fun mapToDomainError(error: Throwable): NetworkingError {
        return when {
            isConnectionTimeout(error) -> NetworkingError.OperationTimeout
            cannotReachHost(error) -> NetworkingError.HostUnreachable
            else -> NetworkingError.ConnectionSpike
        }
    }

    private fun isNetworkingError(error: Throwable) =
        isConnectionTimeout(error) || cannotReachHost(error) || isRequestCanceled(error)

    private fun isRequestCanceled(error: Throwable) =
        error is IOException && error.message?.contentEquals("Canceled") ?: false

    private fun cannotReachHost(error: Throwable) =
        error is UnknownHostException ||
            error is ConnectException ||
            error is NoRouteToHostException

    private fun isConnectionTimeout(error: Throwable) =
        error is SocketTimeoutException
}
