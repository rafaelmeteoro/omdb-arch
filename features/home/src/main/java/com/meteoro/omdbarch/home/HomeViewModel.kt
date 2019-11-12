package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.components.StateMachine
import com.meteoro.omdbarch.components.ViewState
import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.ManagerSearch
import io.reactivex.Flowable

class HomeViewModel(
    private val fetch: FetchSearch,
    private val manager: ManagerSearch,
    private val machine: StateMachine<HomePresentation>
) {
    fun searchMovie(movieTitle: String): Flowable<ViewState<HomePresentation>> =
        fetch.searchMovies(movieTitle)
            .map { BuildHomePresentation(it) }
            .compose(machine)
            .doOnComplete { saveSearch(movieTitle) }

    private fun saveSearch(search: String) {
        if (search.isNotEmpty()) manager.save(search)
    }
}