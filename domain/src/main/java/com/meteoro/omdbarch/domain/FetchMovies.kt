package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.RemoteOmdbService

class FetchMovies(
    private val omdbService: RemoteOmdbService
) {
    fun search(title: String, type: String? = null, year: String? = null) =
        when {
            title.isEmpty() -> throw EmptyTerm
            else -> {
                val movies = omdbService.searchMovies(title, type, year)
                if (movies.isEmpty()) throw NoResultsFound else movies
            }
        }

    fun fetch(id: String) =
        when {
            id.isEmpty() -> throw EmptyTerm
            else -> omdbService.fetchMovie(id)
        }
}