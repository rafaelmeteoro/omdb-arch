package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieCacheService
import io.reactivex.Observable

class CacheMovie(private val service: MovieCacheService) {

    fun saveMovie(movie: Movie) {
        service.save(movie)
    }

    fun deleteMovie(movie: Movie) {
        service.delete(movie)
    }

    fun getMovie(imdbId: String) =
        service.movieCached(imdbId)

    fun getMovies() =
        service.moviesCached()
            .flatMap { movies ->
                if (movies.isEmpty()) {
                    Observable.error(NoResultsFound)
                } else {
                    Observable.just(movies)
                }
            }
}