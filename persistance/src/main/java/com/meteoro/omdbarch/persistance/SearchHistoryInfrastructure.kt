package com.meteoro.omdbarch.persistance

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.meteoro.omdbarch.domain.errors.SearchHistoryError
import com.meteoro.omdbarch.domain.services.SearchHistoryService

class SearchHistoryInfrastructure(private val prefs: SharedPreferences) : SearchHistoryService {

    override fun lastSearches(): List<String> {
        return retrieveFromPrefs().toList()
    }

    @SuppressLint("ApplySharedPref")
    override fun registerNewSearch(search: String) {
        val updated = retrieveFromPrefs() + search
        prefs.edit()
            .putStringSet(KEY_VALUES, updated)
            .commit()
    }

    @SuppressLint("ApplySharedPref")
    override fun unregisterSearch(search: String) {
        val updated = retrieveFromPrefs() - search
        prefs.edit()
            .putStringSet(KEY_VALUES, updated)
            .commit()
    }

    private fun retrieveFromPrefs() =
        prefs.getStringSet(KEY_VALUES, emptySet())
            ?.let { it }
            ?: throw SearchHistoryError

    private companion object {
        const val KEY_VALUES = "key.search.values"
    }
}