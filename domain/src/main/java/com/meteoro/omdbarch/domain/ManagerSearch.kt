package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import io.reactivex.Flowable

class ManagerSearch(private val service: SearchHistoryService) {

    fun save(search: String) {
        service.registerNewSearch(search)
    }

    fun delete(search: String) {
        service.unregisterSearch(search)
    }

    fun fetchSearchList(): Flowable<List<String>> =
        with(service.lastSearches()) {
            if (isEmpty()) Flowable.error(NoResultsFound)
            else Flowable.just(this)
        }
}