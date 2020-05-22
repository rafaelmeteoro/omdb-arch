package com.meteoro.omdbarch.domain.repository

import com.meteoro.omdbarch.domain.model.Movie
import io.reactivex.Flowable

interface CacheRepository {
    fun saveMovie(movie: Movie)
    fun deleteMovie(movie: Movie)
    fun deleteAll()
    fun getMovie(imdbId: String): Flowable<Movie>
    fun getMovies(): Flowable<List<Movie>>
}
