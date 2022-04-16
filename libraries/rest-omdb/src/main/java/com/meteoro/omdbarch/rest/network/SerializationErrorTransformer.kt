package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import kotlinx.serialization.SerializationException
import org.reactivestreams.Publisher
import timber.log.Timber

class SerializationErrorTransformer<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.onErrorResumeNext(this::handleSerializationError)
    }

    private fun handleSerializationError(error: Throwable): Flowable<T> {
        error.message?.let { Timber.e(it) } ?: error.printStackTrace()

        val mapped = when (error) {
            is SerializationException -> RemoteServiceIntegrationError.UnexpectedResponse
            else -> error
        }

        return Flowable.error(mapped)
    }
}
