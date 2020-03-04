package com.meteoro.omdbarch.rest.executor

import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.services.ConnectivityService
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class RemoteExecutorImpl(
    private val service: ConnectivityService,
    private val targetScheduler: Scheduler = Schedulers.trampoline()
) : RemoteExecutor {

    override fun <T> checkConnectionAndThanFlowable(action: Flowable<T>): Flowable<T> {
        return when (service.isConnected()) {
            false -> Flowable.error(NetworkingError.NoInternetConnection)
            true -> action
                .subscribeOn(targetScheduler)
        }
    }
}