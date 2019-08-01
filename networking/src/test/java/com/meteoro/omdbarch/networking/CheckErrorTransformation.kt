package com.meteoro.omdbarch.networking

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class CheckErrorTransformation<T : Any?>(
    private val from: Throwable,
    private val expected: Throwable,
    private val transformer: ObservableTransformer<T, T>
) {
    private fun runCheck() {
        val execution = Observable.error<T>(from).compose(transformer)
        execution.test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(expected)
    }

    companion object {
        fun <T> checkTransformation(from: Throwable, expected: Throwable, using: ObservableTransformer<T, T>) =
            CheckErrorTransformation(from, expected, using).runCheck()
    }
}