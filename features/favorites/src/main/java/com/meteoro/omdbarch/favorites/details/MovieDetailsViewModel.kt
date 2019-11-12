package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.components.StateMachine
import com.meteoro.omdbarch.components.ViewState
import com.meteoro.omdbarch.domain.CacheMovie
import io.reactivex.Flowable

class MovieDetailsViewModel(
    private val cache: CacheMovie,
    private val machine: StateMachine<MovieDetailsPresentation>
) {
    fun fetchMovieSaved(imdbId: String): Flowable<ViewState<MovieDetailsPresentation>> =
        cache.getMovie(imdbId)
            .map { BuildMovieDetailsPresentation(it) }
            .compose(machine)
}