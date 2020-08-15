package com.meteoro.omdbarch.coroutines.testutils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

fun <T> Flow<T>.test(scope: CoroutineScope = GlobalScope, block: FlowTest<T>.() -> Unit) {
    val emissions = mutableListOf<T>()
    val job = scope.launch { toList(emissions) }
    FlowTest(job, emissions).apply(block)
}

class FlowTest<T>(private val parentJob: Job, private val emissions: List<T>) {

    fun triggerEmissions(action: suspend () -> Job) {
        runBlocking { action().join() }
    }

    fun afterCollect(verification: (List<T>) -> Unit) {
        parentJob.invokeOnCompletion {
            verification.invoke(emissions)
        }
    }

    companion object {
        fun <T> flowTest(
            target: Flow<T>,
            scope: CoroutineScope = GlobalScope,
            block: FlowTest<T>.() -> Unit
        ) {
            target.test(scope, block)
        }
    }
}