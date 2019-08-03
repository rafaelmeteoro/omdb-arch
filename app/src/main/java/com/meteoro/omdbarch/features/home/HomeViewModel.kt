package com.meteoro.omdbarch.features.home

import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.utilities.StateMachine

class HomeViewModel(
    private val fetch: FetchSearch,
    private val machine: StateMachine<HomePresentation>
) {
    fun searchMovie(movieTitle: String) =
        fetch.searchMovies(movieTitle)
            .map { BuildHomePresentation(it) }
            .compose(machine)
}