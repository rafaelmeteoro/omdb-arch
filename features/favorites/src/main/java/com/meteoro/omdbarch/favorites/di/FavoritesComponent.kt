package com.meteoro.omdbarch.favorites.di

import com.meteoro.omdbarch.domain.di.ActivityScope
import com.meteoro.omdbarch.favorites.FavoritesActivity
import com.meteoro.omdbarch.favorites.list.MovieListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface FavoritesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: FavoritesActivity): FavoritesComponent
    }

    fun inject(activity: FavoritesActivity)
    fun inject(fragment: MovieListFragment)
}
