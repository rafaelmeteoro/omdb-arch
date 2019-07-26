package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.ClientOrigin
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.RemoteSystem
import com.meteoro.omdbarch.networking.CheckErrorTransformation.Companion.checkTransformation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class HttpIntegrationErrorTransformerTests {

    @Test
    fun `should transform proper throwable into client origin error`() {
        checkTransformation(
            from = httpException<Any>(418, "Teapot"),
            using = HttpIntegrationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(ClientOrigin) }
        )
    }

    @Test
    fun `should transform proper throwable into remote system error`() {
        checkTransformation(
            from = httpException<Any>(503, "Internal Server Error"),
            using = HttpIntegrationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(RemoteSystem) }
        )
    }

    @Test
    fun `should propagate any other error`() {
        val otherError = IllegalStateException("Houston, we hava a problem!")

        checkTransformation(
            from = otherError,
            using = HttpIntegrationErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(otherError) }
        )
    }

    private fun <T> httpException(statusCode: Int, errorMessage: String): HttpException {
        val jsonMediaType = "application/json".toMediaTypeOrNull()
        val body = errorMessage.toResponseBody(jsonMediaType)
        return HttpException(Response.error<T>(statusCode, body))
    }
}