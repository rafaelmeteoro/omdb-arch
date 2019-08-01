package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.UnexpectedResponse
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException

class SerializationErrorTransformer<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.onErrorResumeNext(this::asSerializationError)
    }

    private fun asSerializationError(error: Throwable) = Observable.error<T>(
        mapToDomainerror(error)
    )

    private fun mapToDomainerror(error: Throwable): Throwable =
        when (error) {
            is MissingFieldException,
            is UnknownFieldException,
            is SerializationException -> UnexpectedResponse
            else -> error
        }
}