package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.rest.network.CorCheckErrorTransformation.Companion.corCheckTransformation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

internal class CorHttpIntegrationErrorTransformerTest {

    @Test
    fun `should transform proper throwable into client origin error`() {
        corCheckTransformation(
            from = httpException<Any>(418, "Teapot"),
            using = CorHttpIntegrationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(RemoteServiceIntegrationError.ClientOrigin) }
        )
    }

    @Test
    fun `should transform proper throwable into remote system error`() {
        corCheckTransformation(
            from = httpException<Any>(503, "Internal Server Error"),
            using = CorHttpIntegrationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(RemoteServiceIntegrationError.RemoteSystem) }
        )
    }

    @Test
    fun `should propagate any other error`() {
        val otherError = IllegalStateException("Houston, we have a problem!")

        corCheckTransformation(
            from = otherError,
            using = CorHttpIntegrationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(otherError) }
        )
    }

    private fun <T> httpException(statusCode: Int, errorMessage: String): HttpException {
        val jsonMediaType = "application/json".toMediaTypeOrNull()
        val body = errorMessage.toResponseBody(jsonMediaType)
        return HttpException(Response.error<T>(statusCode, body))
    }
}