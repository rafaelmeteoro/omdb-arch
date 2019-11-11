package com.meteoro.omdbarch.navigator

import android.app.Activity

abstract class MyNavigator {

    companion object {
        const val ARG_MOVIE = "arg_movie"
    }

    open fun navigateToHome(activity: Activity) {}

    open fun navigateToDetail(activity: Activity, imdbId: String) {}

    open fun navigateToFavorites(activity: Activity) {}
}