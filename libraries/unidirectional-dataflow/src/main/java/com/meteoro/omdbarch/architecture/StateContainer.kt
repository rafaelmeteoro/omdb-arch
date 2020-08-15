package com.meteoro.omdbarch.architecture

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

interface StateContainer<T> : ViewStateRegistry<T>, ViewStatesEmmiter<T> {

    @FlowPreview
    @ExperimentalCoroutinesApi
    class Unbounded<T>(scopeToBound: CoroutineScope) : StateContainer<T> {

        private val broadcaster by lazy {
            ConflatedBroadcastChannel<ViewState<T>>(ViewState.FirstLaunch)
        }

        override val emissionScope: CoroutineScope = scopeToBound

        override fun observableStates(): Flow<ViewState<T>> = broadcaster.asFlow()

        override fun current(): ViewState<T> = broadcaster.value

        override suspend fun store(state: ViewState<T>) {
            broadcaster.send(state)
        }
    }
}
