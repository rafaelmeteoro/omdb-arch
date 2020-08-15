package com.meteoro.omdbarch.architecture

import kotlinx.coroutines.*

interface TaskExecutor {

    fun execute(block: suspend TaskExecutor.() -> Unit): Job

    class Concurrent(
        private val scope: CoroutineScope,
        private val dispatcher: CoroutineDispatcher
    ) : TaskExecutor {
        override fun execute(block: suspend TaskExecutor.() -> Unit): Job =
            scope.launch(dispatcher) {
                block.invoke(this@Concurrent)
            }
    }

    class Synchronous(
        private val scope: CoroutineScope
    ) : TaskExecutor {
        override fun execute(block: suspend TaskExecutor.() -> Unit): Job =
            runBlocking {
                scope.launch { block.invoke(this@Synchronous) }
            }
    }
}