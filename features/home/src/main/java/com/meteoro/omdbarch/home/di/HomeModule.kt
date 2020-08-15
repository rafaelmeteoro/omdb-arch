package com.meteoro.omdbarch.home.di

import com.meteoro.omdbarch.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

//    companion object {
//
//        @Provides
//        @Singleton
//        fun provideHomeViewModel(
//            fetch: SearchRepository,
//            manager: ManagerRepository,
//            scheduler: Scheduler
//        ): HomeViewModel =
//            HomeViewModel(
//                searchRepository = fetch,
//                managerRepository = manager,
//                machine = StateMachine(
//                    uiScheduler = scheduler
//                )
//            )
//
//        @Provides
//        @Singleton
//        fun provideHomeContainerViewModel(activity: HomeActivity, fetch: SearchRepository): HomeContainerViewModel {
//
//            val stateContainer = ConfigChangesAwareStateContainer<HomePresentation>(
//                host = activity
//            )
//
//            val stateMachine = StateMachineContainer(
//                container = stateContainer
//            )
//
//            return HomeContainerViewModel(
//                searchRepository = fetch,
//                machine = stateMachine
//            )
//        }
//    }
}
