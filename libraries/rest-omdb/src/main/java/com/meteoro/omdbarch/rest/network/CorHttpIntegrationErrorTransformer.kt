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
            in 400..499 -> RemoteServiceIntegrationError.ClientOrigin
            else -> RemoteServiceIntegrationError.RemoteSystem
        }
}