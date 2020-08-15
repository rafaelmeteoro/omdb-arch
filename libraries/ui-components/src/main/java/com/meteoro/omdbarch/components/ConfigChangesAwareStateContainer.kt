package com.meteoro.omdbarch.components

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.meteoro.omdbarch.architecture.StateContainer
import com.meteoro.omdbarch.architecture.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@Suppress("UNCHECKED_CAST")
@FlowPreview
@ExperimentalCoroutinesApi
class ConfigChangesAwareStateContainer<T> : StateContainer<T>, ViewModel() {

    private val broadcaster by lazy {
        ConflatedBroadcastChannel<ViewState<T>>(ViewState.FirstLaunch)
    }

    override val emissionScope: CoroutineScope = viewModelScope

    override fun observableStates(): Flow<ViewState<T>> = broadcaster.asFlow()

    override fun current(): ViewState<T> = broadcaster.value

    override suspend fun store(state: ViewState<T>) {
        broadcaster.send(state)
    }

    companion object {
        operator fun <T> invoke(host: FragmentActivity): StateContainer<T> {

            val factory = object : ViewModelProvider.Factory {
                override fun <Model : ViewModel?> create(modelClass: Class<Model>) =
                    ConfigChangesAwareStateContainer<T>() as Model
            }

            val keyClazz = ConfigChangesAwareStateContainer::class.java
            return ViewModelProvider(host, factory)[keyClazz] as StateContainer<T>
        }
    }
}