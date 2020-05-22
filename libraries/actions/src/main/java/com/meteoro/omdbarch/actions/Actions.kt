package com.meteoro.omdbarch.actions

import android.content.Context
import android.content.Intent

object Actions {
    private fun internalIntent(context: Context, action: String) = Intent(action).setPackage(context.packageName)

    fun openHomeIntent(context: Context): Intent =
        internalIntent(context, "com.meteoro.omdbarch.home.open")

    fun openDetailIntent(context: Context, imdbId: String): Intent =
        internalIntent(context, "com.meteoro.omdbarch.details.open")
            .putExtra(EXTRA_IMDB, ImdbArgs(imdbId))

    fun openFavoritesIntent(context: Context): Intent =
        internalIntent(context, "com.meteoro.omdbarch.favorites.open")
}
