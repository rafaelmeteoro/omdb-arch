package com.meteoro.omdbarch.home.di

import com.meteoro.omdbarch.architecture.StateMachineContainer
import com.meteoro.omdbarch.architecture.TaskExecutor
import com.meteoro.omdbarch.components.ConfigChangesAwareStateContainer
import com.meteoro.omdbarch.domain.repository.CorSearchRepository
import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.repository.SearchRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.home.HomeActivity
import com.meteoro.omdbarch.home.HomeContainerViewModel
import com.meteoro.omdbarch.home.HomePresentation
import com.meteoro.omdbarch.home.HomeViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Module
class HomeActivityModule {

    @Provides
    fun provideHomeViewModel(
        fetch: SearchRepository,
        manager: ManagerRepository,
        scheduler: Scheduler
    ): HomeViewModel =
        HomeViewModel(
            searchRepository = fetch,
            managerRepository = manager,
            machine = StateMachine(
                uiScheduler = scheduler
            )
        )

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Provides
    fun provideHomeContainerViewModel(
        activity: HomeActivity,
        fetch: CorSearchRepository,
        manager: ManagerRepository
    ): HomeContainerViewModel {

        val stateContainer = ConfigChangesAwareStateContainer<HomePresentation>(
            host = activity
        )

        val stateMachine = StateMachineContainer(
            container = stateContainer,
            executor = TaskExecutor.Concurrent(
                scope = stateContainer.emissionScope,
                dispatcher = Dispatchers.IO
            )
        )

        return HomeContainerViewModel(
            searchRepository = fetch,
            managerRepository = manager,
            machine = stateMachine
        )
    }
}