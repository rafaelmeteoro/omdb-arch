package com.meteoro.omdbarch.navigator

sealed class Screen {

    object HomeMovies : Screen()
    object DetailMovie : Screen()

    override fun toString(): String = when (this) {
        HomeMovies -> "Home Screen"
        DetailMovie -> "Detail Screen"
    }
}