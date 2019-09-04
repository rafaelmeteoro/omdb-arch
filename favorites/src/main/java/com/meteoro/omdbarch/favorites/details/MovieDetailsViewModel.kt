package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.utilities.StateMachine

class MovieDetailsViewModel(
    private val cache: CacheMovie,
    private val machine: StateMachine<MovieDetailsPresentation>
) {
    fun fetchMovieSaved(imdbId: String) =
        cache.getMovie(imdbId)
            .map { BuildMovieDetailsPresentation(it) }
            .compose(machine)
}