package com.meteoro.omdbarch.favorites.di

import com.meteoro.omdbarch.favorites.FavoritesActivity
import com.meteoro.omdbarch.favorites.details.MovieDetailsFragment
import com.meteoro.omdbarch.favorites.list.MovieListFragment
import com.meteoro.omdbarch.favorites.words.WordsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

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
}