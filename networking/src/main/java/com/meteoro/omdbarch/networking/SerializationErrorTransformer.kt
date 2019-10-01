package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.UnexpectedResponse
import com.meteoro.omdbarch.logger.Logger
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException
import org.reactivestreams.Publisher

class SerializationErrorTransformer<T>(private val logger: Logger) : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.onErrorResumeNext(this::handleSerializationError)
    }

    private fun handleSerializationError(error: Throwable): Flowable<T> {
        error.message?.let { logger.e(it) } ?: error.printStackTrace()

        val mapped = when (error) {
            is MissingFieldException,
            is UnknownFieldException,
            is SerializationException -> UnexpectedResponse
            else -> error
        }

        return Flowable.error(mapped)
    }
}