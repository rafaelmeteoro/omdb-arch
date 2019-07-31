package com.meteoro.omdbarch.networking

import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.UnexpectedResponse
import com.meteoro.omdbarch.networking.CheckErrorTransformation.Companion.checkTransformation
import com.nhaarman.mockitokotlin2.any
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SerializationErrorTransformerTests {

    @Test
    fun `should handle json io error`() {
        checkTransformation(
            from = JsonIOException("IO Exception"),
            using = SerializationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(UnexpectedResponse) }
        )
    }

    @Test
    fun `should handle json parse error`() {
        checkTransformation(
            from = JsonParseException("Parse Exception"),
            using = SerializationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(UnexpectedResponse) }
        )
    }

    @Test
    fun `should handle json syntax error`() {
        checkTransformation(
            from = JsonSyntaxException("Syntax Exception"),
            using = SerializationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(UnexpectedResponse) }
        )
    }

    @Test
    fun `should handle missing field error`() {
        checkTransformation(
            from = MissingFieldException("Missing field inside this JSON"),
            using = SerializationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(UnexpectedResponse) }
        )
    }

    @Test
    fun `should handle unknown field error`() {
        checkTransformation(
            from = UnknownFieldException(any()),
            using = SerializationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(UnexpectedResponse) }
        )
    }

    @Test
    fun `should handle serialization error`() {
        checkTransformation(
            from = SerializationException("Found comments inside this JSON"),
            using = SerializationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(UnexpectedResponse) }
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