package com.meteoro.omdbarch.features.details

import com.meteoro.omdbarch.common.ResourceProvider
import com.meteoro.omdbarch.domain.FetchMovie
import com.meteoro.omdbarch.utilities.StateMachine

class DetailViewModel(
    private val fetch: FetchMovie,
    private val machine: StateMachine<MovieDetailPresentation>,
    private val resProvider: ResourceProvider
) {
    fun fetchMovie(idImdb: String) =
        fetch.fetchMovie(idImdb)
            .map { BuildMovieDetailPresentation(it, resProvider) }
            .compose(machine)
}