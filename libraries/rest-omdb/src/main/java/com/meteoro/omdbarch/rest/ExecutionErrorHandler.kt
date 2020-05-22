package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.rest.network.HttpIntegrationErrorTransformer
import com.meteoro.omdbarch.rest.network.NetworkingErrorTransformer
import com.meteoro.omdbarch.rest.network.SerializationErrorTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher

class ExecutionErrorHandler<T>(private val logger: Logger) : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> =
        upstream
            .compose(HttpIntegrationErrorTransformer())
            .compose(NetworkingErrorTransformer())
            .compose(SerializationErrorTransformer(logger))
            .doOnError { logger.e("API integration | Failed with -> $it") }
            .doOnNext { logger.v("API integration -> Success") }
}
