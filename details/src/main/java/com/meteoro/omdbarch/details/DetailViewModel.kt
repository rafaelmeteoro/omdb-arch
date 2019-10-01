package com.meteoro.omdbarch.details

import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.domain.FetchMovie
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.utilities.ResourceProvider
import com.meteoro.omdbarch.utilities.StateMachine
import com.meteoro.omdbarch.utilities.ViewState
import io.reactivex.Flowable

class DetailViewModel(
    private val fetch: FetchMovie,
    private val cache: CacheMovie,
    private val machine: StateMachine<MovieDetailPresentation>,
    private val resProvider: ResourceProvider
) {
    fun fetchMovie(idImdb: String): Flowable<ViewState<MovieDetailPresentation>> =
        fetch.fetchMovie(idImdb)
            .flatMap { movie ->
                saveMovie(movie)
                Flowable.just(movie)
            }
            .map { BuildMovieDetailPresentation(it, resProvider) }
            .compose(machine)

    private fun saveMovie(movie: Movie) {
        cache.saveMovie(movie)
    }
}