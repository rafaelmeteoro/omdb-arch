package com.meteoro.omdbarch.favorites.di

import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.favorites.FavoritesActivity
import com.meteoro.omdbarch.favorites.details.MovieDetailsFragment
import com.meteoro.omdbarch.favorites.list.MovieListFragment
import com.meteoro.omdbarch.favorites.words.WordsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MyActivityModule {

    @Binds
    abstract fun provideFavoritesActivity(activity: FavoritesActivity): AppCompatActivity
}
