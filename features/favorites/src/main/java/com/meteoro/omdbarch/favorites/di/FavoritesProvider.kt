package com.meteoro.omdbarch.favorites.di

import com.meteoro.omdbarch.favorites.FavoritesActivity
import com.meteoro.omdbarch.favorites.details.MovieDetailsFragment
import com.meteoro.omdbarch.favorites.list.MovieListFragment
import com.meteoro.omdbarch.favorites.words.WordsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoritesProvider {

    @ContributesAndroidInjector(modules = [MyActivityModule::class, MyModule::class])
    abstract fun bindFavoritesActivity(): FavoritesActivity

    @ContributesAndroidInjector(modules = [MyModule::class])
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector(modules = [MyModule::class])
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment

    @ContributesAndroidInjector(modules = [MyModule::class])
    abstract fun contributeWordsFragment(): WordsFragment
}
