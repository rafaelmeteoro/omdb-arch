package com.meteoro.omdbarch.favorites.di

import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.favorites.FavoritesActivity
import com.meteoro.omdbarch.favorites.details.MovieDetailsFragment
import com.meteoro.omdbarch.favorites.list.MovieListFragment
import com.meteoro.omdbarch.favorites.words.WordsFragment
import com.meteoro.omdbarch.favorites.words.WordsViewModel
import com.meteoro.omdbarch.utilities.StateMachine
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
        fun provideWordsViewModel(manager: ManagerSearch, scheduler: Scheduler): WordsViewModel =
            WordsViewModel(
                manager = manager,
                machine = StateMachine(
                    uiScheduler = scheduler
                )
            )
    }
}