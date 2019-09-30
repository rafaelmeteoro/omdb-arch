package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.ClientOrigin
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.RemoteSystem
import com.meteoro.omdbarch.networking.CheckErrorFlowableTransformation.Companion.checkSingleTransformation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class HttpIntegrationErrorFlowableTransformerTests {

    @Test
    fun `should transform proper throwable into client origin error`() {
        checkSingleTransformation(
            from = httpException<Any>(418, "Teapot"),
            expected = ClientOrigin,
            using = HttpIntegrationErrorFlowableTransformer<Any>()
        )
    }

    @Test
    fun `should transform proper throwable into remote system error`() {
        checkSingleTransformation(
            from = httpException<Any>(503, "Internal Server Error"),
            expected = RemoteSystem,
            using = HttpIntegrationErrorFlowableTransformer<Any>()
        )
    }

    @Test
    fun `should propagate any other error`() {
        val otherError = IllegalStateException("Houston, we have a problem!")

        checkSingleTransformation(
            from = otherError,
            expected = otherError,
            using = HttpIntegrationErrorFlowableTransformer<Any>()
        )
    }

    private fun <T> httpException(statusCode: Int, errorMessage: String): HttpException {
        val jsonMediaType = "application/json".toMediaTypeOrNull()
        val body = errorMessage.toResponseBody(jsonMediaType)
        return HttpException(Response.error<T>(statusCode, body))
    }
}