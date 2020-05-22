package com.meteoro.omdbarch.persistance

import android.app.Application
import android.content.Context.MODE_PRIVATE

internal class AppPreferencesWrapper(private val app: Application) {

    private companion object {
        const val PREFS_FILE = "last-searches"
    }

    val preferences by lazy {
        app.getSharedPreferences(PREFS_FILE, MODE_PRIVATE)
    }
}
