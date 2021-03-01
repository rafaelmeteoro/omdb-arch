package com.meteoro.omdbarch.favorites.di

import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.favorites.FavoritesActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MyActivityModule {

    @Binds
    abstract fun provideFavoritesActivity(activity: FavoritesActivity): AppCompatActivity
}
