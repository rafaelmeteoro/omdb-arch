package com.meteoro.omdbarch.networking

import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.meteoro.omdbarch.domain.errors.ErrorTransformer
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.UnexpectedResponse
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException

object SerializationErrorTransformer : ErrorTransformer {

    override fun transform(incoming: Throwable): Throwable =
        when (incoming) {
            is JsonIOException,
            is JsonParseException,
            is JsonSyntaxException,
            is MalformedJsonException,
            is MissingFieldException,
            is UnknownFieldException,
            is SerializationException -> UnexpectedResponse
            else -> incoming
        }
}