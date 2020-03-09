package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import io.reactivex.Flowable

class ManagerSearch(private val service: SearchHistoryService) : ManagerRepository {

    override fun save(search: String) {
        service.registerNewSearch(search)
    }

    override fun delete(search: String) {
        service.unregisterSearch(search)
    }

    override fun fetchSearchList(): Flowable<List<String>> =
        with(service.lastSearches()) {
            if (isEmpty()) Flowable.error(NoResultsFound)
            else Flowable.just(this)
        }
}