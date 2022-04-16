package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.rest.network.CheckErrorTransformation.Companion.checkTransformation
import kotlinx.serialization.SerializationException
import org.junit.Test

internal class SerializationErrorTransformerTest {

    @Test
    fun `should handle serialization error`() {
        checkTransformation(
            from = SerializationException("Found comments inside this JSON"),
            expected = RemoteServiceIntegrationError.UnexpectedResponse,
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
