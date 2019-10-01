package com.meteoro.omdbarch.utilities

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

class StateMachine<T>(private val uiScheduler: Scheduler = Schedulers.trampoline()) :
    FlowableTransformer<T, ViewState<T>> {

    override fun apply(upstream: Flowable<T>): Publisher<ViewState<T>> {

        val beggining = ViewState.Launched
        val end = Flowable.just(ViewState.Done)

        return upstream
            .map { value: T -> ViewState.Success(value) as ViewState<T> }
            .onErrorReturn { error: Throwable -> ViewState.Failed(error) }
            .startWith(beggining)
            .concatWith(end)
            .observeOn(uiScheduler)
    }
}