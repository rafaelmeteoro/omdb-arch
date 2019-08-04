package com.meteoro.omdbarch.utilities

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class StateMachine<T>(private val uiScheduler: Scheduler = Schedulers.trampoline()) :
    ObservableTransformer<T, ViewState<T>> {

    override fun apply(upstream: Observable<T>): ObservableSource<ViewState<T>> {

        val beggining = ViewState.Launched
        val end = Observable.just(ViewState.Done)

        return upstream
            .map { value: T -> ViewState.Success(value) as ViewState<T> }
            .onErrorReturn { error: Throwable -> ViewState.Failed(error) }
            .startWith(beggining)
            .concatWith(end)
            .observeOn(uiScheduler)
    }
}