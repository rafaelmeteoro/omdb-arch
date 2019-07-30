package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.Movie
import io.reactivex.Observable

interface RemoteOmdbService {
    fun searchMovies(
        title: String,
        type: String? = null,
        year: String? = null
    ): Observable<List<Movie>>

    fun fetchMovie(id: String): Observable<Movie>
}