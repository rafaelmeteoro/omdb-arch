package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.ErrorTransformer
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import kotlinx.serialization.SerializationException

object CorSerializationErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Throwable): Throwable =
        when (incoming) {
            is SerializationException -> RemoteServiceIntegrationError.UnexpectedResponse
            else -> incoming
        }
}
