package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieCacheService
import io.reactivex.Flowable

class CacheMovie(private val service: MovieCacheService) {

    fun saveMovie(movie: Movie) {
        service.save(movie)
    }

    fun deleteMovie(movie: Movie) {
        service.delete(movie)
    }

    fun deleteAll() {
        service.deleteAll()
    }

    fun getMovie(imdbId: String): Flowable<Movie> =
        service.movieCached(imdbId)

    fun getMovies(): Flowable<List<Movie>> =
        service.moviesCached()
            .flatMap { movies ->
                if (movies.isEmpty()) {
                    Flowable.error(NoResultsFound)
                } else {
                    Flowable.just(movies)
                }
            }
}