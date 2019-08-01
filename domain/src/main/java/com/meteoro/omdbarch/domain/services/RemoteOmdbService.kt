package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import io.reactivex.Observable

interface RemoteOmdbService {
    fun searchMovies(title: String, type: String? = null, year: String? = null): Observable<ResultSearch>
    fun fetchMovie(id: String): Observable<Movie>
}