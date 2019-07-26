package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.ErrorTransformer
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import retrofit2.HttpException

object HttpIntegrationErrorTransformer : ErrorTransformer {

    override fun transform(incoming: Throwable): Throwable =
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