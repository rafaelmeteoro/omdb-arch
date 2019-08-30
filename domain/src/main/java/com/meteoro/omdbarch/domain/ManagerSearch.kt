package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import io.reactivex.Observable

class ManagerSearch(private val service: SearchHistoryService) {

    fun save(search: String) {
        service.registerNewSearch(search)
    }

    fun fetchSearchList() =
        with(service.lastSearches()) {
            if (isEmpty()) Observable.error(NoResultsFound)
            else Observable.just(this)
        }
}