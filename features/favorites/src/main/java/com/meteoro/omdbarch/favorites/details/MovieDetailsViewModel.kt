package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
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