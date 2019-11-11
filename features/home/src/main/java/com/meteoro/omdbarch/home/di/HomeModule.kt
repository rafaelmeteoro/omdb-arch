package com.meteoro.omdbarch.home.di

import com.meteoro.omdbarch.components.StateMachine
import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.domain.services.SearchService
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
        fun provideFetchSearch(service: SearchService): FetchSearch =
            FetchSearch(service)

        @JvmStatic
        @Provides
        @Singleton
        fun provideHomeViewModel(fetch: FetchSearch, manager: ManagerSearch, scheduler: Scheduler): HomeViewModel =
            HomeViewModel(
                fetch = fetch,
                manager = manager,
                machine = StateMachine(
                    uiScheduler = scheduler
                )
            )
    }
}