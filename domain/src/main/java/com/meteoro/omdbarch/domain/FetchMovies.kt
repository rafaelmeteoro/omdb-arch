package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import io.reactivex.Observable

class FetchMovies(
    private val omdbService: RemoteOmdbService
) {
    fun search(title: String, type: String? = null, year: String? = null): Observable<List<Movie>> =
        when {
            title.isEmpty() -> Observable.error(EmptyTerm)
            else -> omdbService.searchMovies(title, type, year)
                .switchIfEmpty(Observable.error(NoResultsFound))
        }

    fun fetch(id: String) =
        when {
            id.isEmpty() -> Observable.error(EmptyTerm)
            else -> omdbService.fetchMovie(id)
        }
}