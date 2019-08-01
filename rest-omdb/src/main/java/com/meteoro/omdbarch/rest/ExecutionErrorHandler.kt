package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.networking.HttpIntegrationErrorTransformer
import com.meteoro.omdbarch.networking.NetworkingErrorTransformer
import com.meteoro.omdbarch.networking.SerializationErrorTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class ExecutionErrorHandler<T>(private val logger: Logger) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> =
        upstream
            .compose(HttpIntegrationErrorTransformer())
            .compose(NetworkingErrorTransformer())
            .compose(SerializationErrorTransformer(logger))
            .doOnError { logger.e("API integration | Failed with -> $it") }
            .doOnNext { logger.v("API integration -> Success") }
}