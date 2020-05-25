package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.rest.network.HttpIntegrationErrorTransformer
import com.meteoro.omdbarch.rest.network.NetworkingErrorTransformer
import com.meteoro.omdbarch.rest.network.SerializationErrorTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher
import timber.log.Timber

class ExecutionErrorHandler<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> =
        upstream
            .compose(HttpIntegrationErrorTransformer())
            .compose(NetworkingErrorTransformer())
            .compose(SerializationErrorTransformer())
            .doOnError { Timber.e("API integration | Failed with -> $it") }
            .doOnNext { Timber.v("API integration -> Success") }
}
