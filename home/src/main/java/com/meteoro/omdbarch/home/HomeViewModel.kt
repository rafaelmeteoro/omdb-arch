package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.utilities.StateFlowableMachine
import com.meteoro.omdbarch.utilities.StateMachine
import com.meteoro.omdbarch.utilities.ViewState
import io.reactivex.Flowable

class HomeViewModel(
    private val fetch: FetchSearch,
    private val manager: ManagerSearch,
    private val machine: StateMachine<HomePresentation>,
    private val machineFlowable: StateFlowableMachine<HomePresentation>
) {
    fun searchMovie(movieTitle: String) =
        fetch.searchMovies(movieTitle, "", "")
            .map { BuildHomePresentation(it) }
            .compose(machine)
            .doOnComplete { saveSearch(movieTitle) }

    fun searchMovieFlowable(movieTitle: String): Flowable<ViewState<HomePresentation>> =
        fetch.searchMovies(movieTitle)
            .map { BuildHomePresentation(it) }
            .compose(machineFlowable)
            .doOnComplete { saveSearch(movieTitle) }

    private fun saveSearch(search: String) {
        if (search.isNotEmpty()) manager.save(search)
    }
}