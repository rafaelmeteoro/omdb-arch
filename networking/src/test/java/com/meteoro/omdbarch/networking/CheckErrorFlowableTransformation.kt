package com.meteoro.omdbarch.networking

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer

class CheckErrorFlowableTransformation<T : Any?>(
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
        fun <T> checkSingleTransformation(from: Throwable, expected: Throwable, using: FlowableTransformer<T, T>) =
            CheckErrorFlowableTransformation(from, expected, using).runCheck()
    }
}