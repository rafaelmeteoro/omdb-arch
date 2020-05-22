package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.Movie
import io.reactivex.Flowable

interface MovieCacheService {
    fun save(movie: Movie)
    fun delete(movie: Movie)
    fun deleteAll()
    fun movieCached(imdbId: String): Flowable<Movie>
    fun moviesCached(): Flowable<List<Movie>>
}
