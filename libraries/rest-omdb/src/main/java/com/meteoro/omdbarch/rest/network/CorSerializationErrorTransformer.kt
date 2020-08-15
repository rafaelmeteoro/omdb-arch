package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.ErrorTransformer
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException

object CorSerializationErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Throwable): Throwable =
        when (incoming) {
            is MissingFieldException,
            is UnknownFieldException,
            is SerializationException -> RemoteServiceIntegrationError.UnexpectedResponse
            else -> incoming
        }
}
