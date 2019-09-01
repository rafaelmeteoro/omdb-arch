package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.Movie
import io.reactivex.Observable

interface MovieCacheService {
    fun save(movie: Movie)
    fun movieCached(imdbId: String): Observable<Movie>
    fun moviesCached(): Observable<List<Movie>>
}