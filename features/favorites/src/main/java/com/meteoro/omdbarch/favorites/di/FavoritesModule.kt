package com.meteoro.omdbarch.favorites.di

import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.favorites.FavoritesActivity
import com.meteoro.omdbarch.favorites.details.MovieDetailsFragment
import com.meteoro.omdbarch.favorites.details.MovieDetailsViewModel
import com.meteoro.omdbarch.favorites.list.MovieListFragment
import com.meteoro.omdbarch.favorites.list.MovieListViewModel
import com.meteoro.omdbarch.favorites.words.WordsFragment
import com.meteoro.omdbarch.favorites.words.WordsViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Scheduler
import javax.inject.Singleton

@Module
abstract class FavoritesModule {

    @ContributesAndroidInjector
    abstract fun contributeFavoritesActivity(): FavoritesActivity

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeWordsFragment(): WordsFragment

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideWordsViewModel(manager: ManagerRepository, scheduler: Scheduler): WordsViewModel =
            WordsViewModel(
                managerRepository = manager,
                machine = StateMachine(
                    uiScheduler = scheduler
                )
            )

        @JvmStatic
        @Provides
        @Singleton
        fun provideMovieListViewModel(cache: CacheRepository, scheduler: Scheduler): MovieListViewModel =
            MovieListViewModel(
                cacheRepository = cache,
                machine = StateMachine(
                    uiScheduler = scheduler
                )
            )

        @JvmStatic
        @Provides
        @Singleton
        fun provideMovieDetailsViewModel(cache: CacheRepository, scheduler: Scheduler): MovieDetailsViewModel =
            MovieDetailsViewModel(
                cacheRepository = cache,
                machine = StateMachine(
                    uiScheduler = scheduler
                )
            )
    }
}