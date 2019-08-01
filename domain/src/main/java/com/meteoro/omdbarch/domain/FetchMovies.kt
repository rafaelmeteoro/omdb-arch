package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import io.reactivex.Observable

class FetchMovies(private val omdbService: RemoteOmdbService) {

    fun searchMovies(title: String, type: String? = null, year: String? = null): Observable<ResultSearch> =
        when {
            title.isEmpty() -> Observable.error(EmptyTerm)
            else -> omdbService.searchMovies(title, type, year)
                .flatMap {
                    if (RESULT_OK == it.response) {
                        Observable.just(it)
                    } else {
                        Observable.error(NoResultsFound)
                    }
                }
        }

    fun fetchMovie(id: String): Observable<Movie> =
        when {
            id.isEmpty() -> Observable.error(EmptyTerm)
            else -> omdbService.fetchMovie(id)
        }

    companion object {
        const val RESULT_OK = "True"
    }
}