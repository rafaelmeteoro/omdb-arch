package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.HttpException

class HttpIntegrationErrorTransformer<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.onErrorResumeNext(this::handleIfRestError)
    }

    private fun handleIfRestError(incoming: Throwable): Observable<T> =
        if (incoming is HttpException) toInfrastructureError(incoming)
        else Observable.error(incoming)

    private fun toInfrastructureError(restError: HttpException): Observable<T> {
        val infraError = translateUsingStatusCode(restError.code())
        return Observable.error(infraError)
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