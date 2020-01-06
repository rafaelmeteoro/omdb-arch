package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher
import retrofit2.HttpException

class HttpIntegrationErrorTransformer<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.onErrorResumeNext(this::handleIfRestError)
    }

    private fun handleIfRestError(incoming: Throwable): Flowable<T> =
        if (incoming is HttpException) toInfrastructureError(incoming)
        else Flowable.error(incoming)

    private fun toInfrastructureError(restError: HttpException): Flowable<T> {
        val infraError = translateUsingStatusCode(restError.code())
        return Flowable.error(infraError)
    }

    private fun translateUsingStatusCode(code: Int) = when (code) {
        in FIRST_HTTP_CODE..LAST_HTTP_CODE -> RemoteServiceIntegrationError.ClientOrigin
        else -> RemoteServiceIntegrationError.RemoteSystem
    }

    companion object {
        private const val FIRST_HTTP_CODE = 400
        private const val LAST_HTTP_CODE = 499
    }
}