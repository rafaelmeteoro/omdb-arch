package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.networking.CheckErrorTransformation.Companion.checkTransformation
import kotlinx.serialization.SerializationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SerializationErrorTransformerTests {

    @Test
    fun `should handle serialization error`() {
        val expected = RemoteServiceIntegrationError.UnexpectedResponse
        val error = SerializationException("Found comments inside this JSON")

        checkTransformation(
            from = error,
            using = SerializationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(expected) }
        )
    }

    @Test
    fun `should not handle any other errors`() {
        val otherError = IllegalStateException("Something broke here ...")

        checkTransformation(
            from = otherError,
            using = SerializationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(otherError) }
        )
    }
}