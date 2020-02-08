package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.rest.network.CheckErrorTransformation.Companion.checkTransformation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class HttpIntegrationErrorTransformerTest {

    @Test
    fun `should transform proper throwable into client origin error`() {
        checkTransformation(
            from = httpException<Any>(418, "Teapot"),
            expected = RemoteServiceIntegrationError.ClientOrigin,
            using = HttpIntegrationErrorTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper throwable into remote system error`() {
        checkTransformation(
            from = httpException<Any>(503, "Internal Server Error"),
            expected = RemoteServiceIntegrationError.RemoteSystem,
            using = HttpIntegrationErrorTransformer<Any>()
        )
    }

    @Test
    fun `should propagate any other error`() {
        val otherError = IllegalStateException("Houston, we have a problem!")

        checkTransformation(
            from = otherError,
            expected = otherError,
            using = HttpIntegrationErrorTransformer<Any>()
        )
    }

    private fun <T> httpException(statusCode: Int, errorMessage: String): HttpException {
        val jsonMediaType = "application/json".toMediaTypeOrNull()
        val body = errorMessage.toResponseBody(jsonMediaType)
        return HttpException(Response.error<T>(statusCode, body))
    }
}