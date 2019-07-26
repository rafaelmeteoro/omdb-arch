package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.ErrorTransformer
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.UnexpectedResponse
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException

object SerializationErrorTransformer : ErrorTransformer {

    override fun transform(incoming: Throwable): Throwable =
        when (incoming) {
            is MissingFieldException,
            is UnknownFieldException,
            is SerializationException -> UnexpectedResponse
            else -> incoming
        }
}