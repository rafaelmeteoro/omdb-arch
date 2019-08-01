package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.SearchService
import io.reactivex.Observable

class FetchSearch(private val service: SearchService) {

    fun searchMovies(title: String, type: String? = null, year: String? = null) =
        when {
            title.isEmpty() -> Observable.error(EmptyTerm)
            else -> service.searchMovies(title, type, year)
                .flatMap {
                    if (RESULT_OK == it.response) {
                        Observable.just(it)
                    } else {
                        Observable.error(NoResultsFound)
                    }
                }
        }

    companion object {
        const val RESULT_OK = "True"
    }
}