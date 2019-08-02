package com.meteoro.omdbarch.utilities

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

sealed class UIEvent<out T>

object Launched : UIEvent<Nothing>()
data class Failed(val reason: Throwable) : UIEvent<Nothing>()
data class Result<out T>(val value: T) : UIEvent<T>()
object Done : UIEvent<Nothing>()

class StateMachine<T>(private val uiScheduler: Scheduler = Schedulers.trampoline()) :
    ObservableTransformer<T, UIEvent<T>> {

    override fun apply(upstream: Observable<T>): ObservableSource<UIEvent<T>> {

        val beggining = Launched
        val end = Observable.just(Done)

        return upstream
            .map { value: T -> Result(value) as UIEvent<T> }
            .onErrorReturn { error: Throwable -> Failed(error) }
            .startWith(beggining)
            .concatWith(end)
            .observeOn(uiScheduler)
    }
}