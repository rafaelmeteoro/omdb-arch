package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.ErrorTransformer
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import retrofit2.HttpException

object CorHttpIntegrationErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Throwable): Throwable =
        when (incoming) {
            is HttpException -> translateUsingStatusCode(incoming.code())
            else -> incoming
        }

    private fun translateUsingStatusCode(code: Int) =
        when (code) {
            in FIRST_HTTP_CODE..LAST_HTTP_CODE -> RemoteServiceIntegrationError.ClientOrigin
            else -> RemoteServiceIntegrationError.RemoteSystem
        }

    private const val FIRST_HTTP_CODE = 400
    private const val LAST_HTTP_CODE = 499
}
