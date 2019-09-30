package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.networking.HttpIntegrationErrorFlowableTransformer
import com.meteoro.omdbarch.networking.NetworkingErrorFlowableTransformer
import com.meteoro.omdbarch.networking.SerializationErrorFlowableTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher

class ExecutionErrorHandlerFlowable<T>(private val logger: Logger) : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
            .compose(HttpIntegrationErrorFlowableTransformer())
            .compose(NetworkingErrorFlowableTransformer())
            .compose(SerializationErrorFlowableTransformer(logger))
            .doOnError { logger.e("API integration | Failed with -> $it") }
            .doOnNext { logger.v("API integration -> Success") }
    }
}