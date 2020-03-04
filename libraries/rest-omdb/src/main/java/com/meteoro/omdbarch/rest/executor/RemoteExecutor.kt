package com.meteoro.omdbarch.rest.executor

import io.reactivex.Flowable

interface RemoteExecutor {
    fun <T> checkConnectionAndThanFlowable(action: Flowable<T>): Flowable<T>
}