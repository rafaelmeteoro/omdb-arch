package com.meteoro.omdbarch.favorites.di

interface FavoritesComponentProvider {
    fun provideFavoritesComponent(): FavoritesComponent.Factory
}
