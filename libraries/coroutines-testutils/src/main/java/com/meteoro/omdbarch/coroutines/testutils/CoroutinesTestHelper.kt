package com.meteoro.omdbarch.coroutines.testutils

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.ExternalResource

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class CoroutinesTestHelper : ExternalResource() {

    private val singleThread = newSingleThreadContext("Testing thread")
    val scope = CoroutineScope(singleThread)

    override fun before() {
        Dispatchers.setMain(singleThread)
        super.before()
    }

    override fun after() {
        Dispatchers.resetMain()
        singleThread.close()
        scope.cancel()
        super.after()
    }
}