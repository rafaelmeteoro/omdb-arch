package com.meteoro.omdbarch.details

import com.meteoro.omdbarch.domain.FetchMovie
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.utilities.ResourceProvider
import com.meteoro.omdbarch.utilities.StateMachine
import io.reactivex.Observable

class DetailViewModel(
    private val fetch: FetchMovie,
    private val cache: MovieCacheService,
    private val machine: StateMachine<MovieDetailPresentation>,
    private val resProvider: ResourceProvider
) {
    fun fetchMovie(idImdb: String) =
        fetch.fetchMovie(idImdb)
            .flatMap { movie ->
                saveMovie(movie)
                Observable.just(movie)
            }
            .map { BuildMovieDetailPresentation(it, resProvider) }
            .compose(machine)

    private fun saveMovie(movie: Movie) {
        cache.save(movie)
    }
}