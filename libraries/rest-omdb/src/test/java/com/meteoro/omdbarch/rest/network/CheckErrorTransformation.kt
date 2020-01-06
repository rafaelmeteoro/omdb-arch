package com.meteoro.omdbarch.rest.network

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer

class CheckErrorTransformation<T : Any?>(
    private val from: Throwable,
    private val expected: Throwable,
    private val transformer: FlowableTransformer<T, T>
) {
    private fun runCheck() {
        val execution = Flowable.error<T>(from).compose(transformer)
        execution.test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(expected)
    }

    companion object {
        fun <T> checkTransformation(from: Throwable, expected: Throwable, using: FlowableTransformer<T, T>) =
            CheckErrorTransformation(from, expected, using).runCheck()
    }
}