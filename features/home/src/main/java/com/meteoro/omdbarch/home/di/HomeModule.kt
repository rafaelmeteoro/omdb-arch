package com.meteoro.omdbarch.home.di

import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.repository.SearchRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.home.HomeActivity
import com.meteoro.omdbarch.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Scheduler
import javax.inject.Singleton

@Module
abstract class HomeModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
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
    }
}
