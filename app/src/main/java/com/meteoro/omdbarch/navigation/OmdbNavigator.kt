package com.meteoro.omdbarch.navigation

import android.app.Activity
import android.content.Intent
import com.meteoro.omdbarch.details.MovieDetailActivity
import com.meteoro.omdbarch.home.HomeActivity
import com.meteoro.omdbarch.navigator.MyNavigator

class OmdbNavigator : MyNavigator() {

    override fun navigateToHome(activity: Activity) {
        activity.startActivity(Intent(activity, HomeActivity::class.java))
        activity.finish()
    }

    override fun navigateToDetail(activity: Activity, imdbId: String) {
        activity.startActivity(Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra(ARG_MOVIE, imdbId)
        })
    }
}