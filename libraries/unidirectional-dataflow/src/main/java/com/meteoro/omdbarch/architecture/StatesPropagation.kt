package com.meteoro.omdbarch.architecture

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ViewStatesEmmiter<T> {
    fun observableStates(): Flow<ViewState<T>>
    val emissionScope: CoroutineScope
}

interface ViewStateRegistry<T> {
    fun current(): ViewState<T>
    suspend fun store(state: ViewState<T>)
}