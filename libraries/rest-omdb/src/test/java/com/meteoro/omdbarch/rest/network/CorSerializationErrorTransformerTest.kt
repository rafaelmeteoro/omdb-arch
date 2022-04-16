package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.rest.network.CorCheckErrorTransformation.Companion.corCheckTransformation
import kotlinx.serialization.SerializationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class CorSerializationErrorTransformerTest {

    @Test
    fun `should handle serialization field error`() {
        corCheckTransformation(
            from = SerializationException("Found comments inside this JSON"),
            using = CorSerializationErrorTransformer,
            check = { transformed ->
                assertThat(transformed).isEqualTo(RemoteServiceIntegrationError.UnexpectedResponse)
            }
        )
    }

    @Test
    fun `should not handle any other errors`() {
        val otherError = IllegalStateException("Something broken here ...")

        corCheckTransformation(
            from = otherError,
            using = CorSerializationErrorTransformer,
            check = { transformed ->
                assertThat(transformed).isEqualTo(otherError)
            }
        )
    }
}
