package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.repository.SearchRepository
import com.meteoro.omdbarch.domain.services.SearchService
import io.reactivex.Flowable

class FetchSearch(private val service: SearchService) : SearchRepository {

    override fun searchMovies(title: String, type: String?, year: String?): Flowable<ResultSearch> {
        return when {
            title.isEmpty() -> Flowable.error<ResultSearch>(EmptyTerm)
            else ->
                service.searchMovies(title, type, year)
                    .flatMap {
                        if (RESULT_OK == it.response) {
                            Flowable.just(it)
                        } else {
                            Flowable.error(NoResultsFound)
                        }
                    }
        }
    }

    companion object {
        const val RESULT_OK = "True"
    }
}
