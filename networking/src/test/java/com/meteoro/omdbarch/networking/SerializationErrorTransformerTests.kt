package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.UnexpectedResponse
import com.meteoro.omdbarch.networking.CheckErrorTransformation.Companion.checkTransformation
import com.nhaarman.mockitokotlin2.any
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException
import org.junit.Test

class SerializationErrorTransformerTests {

    @Test
    fun `should handle missing field error`() {
        checkTransformation(
            from = MissingFieldException("Missing field inside this JSON"),
            expected = UnexpectedResponse,
            using = SerializationErrorTransformer<Any>()
        )
    }

    @Test
    fun `should handle unknown field error`() {
        checkTransformation(
            from = UnknownFieldException(any()),
            expected = UnexpectedResponse,
            using = SerializationErrorTransformer<Any>()
        )
    }

    @Test
    fun `should handle serialization error`() {
        checkTransformation(
            from = SerializationException("Found comments inside this JSON"),
            expected = UnexpectedResponse,
            using = SerializationErrorTransformer<Any>()
        )
    }

    @Test
    fun `should not handle any other errors`() {
        val otherError = IllegalStateException("Something broke here ...")

        checkTransformation(
            from = otherError,
            expected = otherError,
            using = SerializationErrorTransformer<Any>()
        )
    }
}