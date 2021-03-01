package com.meteoro.omdbarch.favorites.di

import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.favorites.FavoritesActivity
import com.meteoro.omdbarch.favorites.ObjetoComActivity
import com.meteoro.omdbarch.favorites.details.MovieDetailsViewModel
import com.meteoro.omdbarch.favorites.list.MovieListViewModel
import com.meteoro.omdbarch.favorites.words.WordsViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class MyModule {

    @Provides
//    @Singleton
    fun provideWordsViewModel(manager: ManagerRepository, scheduler: Scheduler): WordsViewModel =
        WordsViewModel(
            managerRepository = manager,
            machine = StateMachine(
                uiScheduler = scheduler
            )
        )

    @Provides
//    @Singleton
    fun provideMovieListViewModel(cache: CacheRepository, scheduler: Scheduler): MovieListViewModel =
        MovieListViewModel(
            cacheRepository = cache,
            machine = StateMachine(
                uiScheduler = scheduler
            )
        )

    @Provides
//    @Singleton
    fun provideMovieDetailsViewModel(cache: CacheRepository, scheduler: Scheduler): MovieDetailsViewModel =
        MovieDetailsViewModel(
            cacheRepository = cache,
            machine = StateMachine(
                uiScheduler = scheduler
            )
        )

    @Provides
    fun provideObjectWithActivity(): ObjetoComActivity =
        ObjetoComActivity()
}
