package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.repository.CorSearchRepository
import com.meteoro.omdbarch.domain.services.CorSearchService

class CorFetchSearch(private val service: CorSearchService) : CorSearchRepository {

    override suspend fun searchMovies(title: String, type: String?, year: String?): ResultSearch =
        when {
            title.isEmpty() -> throw SearchMoviesError.EmptyTerm
            else -> {
                val results = service.searchMovies(title, type, year)
                if (RESULT_OK == results.response) results else throw SearchMoviesError.NoResultsFound
            }
        }

    companion object {
        const val RESULT_OK = "True"
    }
}
