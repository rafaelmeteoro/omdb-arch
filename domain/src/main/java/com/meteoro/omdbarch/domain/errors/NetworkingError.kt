package com.meteoro.omdbarch.domain.errors

sealed class NetworkingError : Throwable() {

    object HostUnreachable : NetworkingError()
    object OperationTimeout : NetworkingError()
    object ConnectionSpike : NetworkingError()
    object NoInternetConnection : NetworkingError()

    override fun toString() = when (this) {
        HostUnreachable -> "Cannot reach remote host"
        OperationTimeout -> "Networking operation timed out"
        ConnectionSpike -> "In-flight networking operation interrupted"
        NoInternetConnection -> "No Internet Connection"
    }
}
