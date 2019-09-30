package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.UnexpectedResponse
import com.meteoro.omdbarch.logger.ConsoleLogger
import com.meteoro.omdbarch.networking.CheckErrorFlowableTransformation.Companion.checkSingleTransformation
import com.nhaarman.mockitokotlin2.any
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException
import org.junit.Test

class SerializationErrorFlowableTransformerTests {

    @Test
    fun `should handle missing field error`() {
        checkSingleTransformation(
            from = MissingFieldException("Missing field inside this JSON"),
            expected = UnexpectedResponse,
            using = SerializationErrorFlowableTransformer<Any>(ConsoleLogger)
        )
    }

    @Test
    fun `should handle unknown field error`() {
        checkSingleTransformation(
            from = UnknownFieldException(any()),
            expected = UnexpectedResponse,
            using = SerializationErrorFlowableTransformer<Any>(ConsoleLogger)
        )
    }

    @Test
    fun `should handle serialization error`() {
        checkSingleTransformation(
            from = SerializationException("Found comments inside this JSON"),
            expected = UnexpectedResponse,
            using = SerializationErrorFlowableTransformer<Any>(ConsoleLogger)
        )
    }

    @Test
    fun `should not handle any other errors`() {
        val otherError = IllegalStateException("Something broke here ...")

        checkSingleTransformation(
            from = otherError,
            expected = otherError,
            using = SerializationErrorFlowableTransformer<Any>(ConsoleLogger)
        )
    }
}