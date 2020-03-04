package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.logger.ConsoleLogger
import com.meteoro.omdbarch.rest.network.CheckErrorTransformation.Companion.checkTransformation
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException
import org.junit.Test

class SerializationErrorTransformerTest {

    @Test
    fun `should handle missing field error`() {
        checkTransformation(
            from = MissingFieldException("Missing field inside this JSON"),
            expected = RemoteServiceIntegrationError.UnexpectedResponse,
            using = SerializationErrorTransformer<Any>(ConsoleLogger)
        )
    }

    @Test
    fun `should handle unknown field error`() {
        checkTransformation(
            from = UnknownFieldException(0),
            expected = RemoteServiceIntegrationError.UnexpectedResponse,
            using = SerializationErrorTransformer<Any>(ConsoleLogger)
        )
    }

    @Test
    fun `should handle serialization error`() {
        checkTransformation(
            from = SerializationException("Found comments inside this JSON"),
            expected = RemoteServiceIntegrationError.UnexpectedResponse,
            using = SerializationErrorTransformer<Any>(ConsoleLogger)
        )
    }

    @Test
    fun `should not handle any other errors`() {
        val otherError = IllegalStateException("Something broke here ...")

        checkTransformation(
            from = otherError,
            expected = otherError,
            using = SerializationErrorTransformer<Any>(ConsoleLogger)
        )
    }
}