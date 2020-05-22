package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.repository.SearchRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import io.reactivex.Flowable

class HomeViewModel(
    private val searchRepository: SearchRepository,
    private val managerRepository: ManagerRepository,
    private val machine: StateMachine<HomePresentation>
) {
    fun searchMovie(movieTitle: String): Flowable<ViewState<HomePresentation>> =
        searchRepository.searchMovies(movieTitle)
            .map { HomePresentationMapper().fromDomain(it) }
            .compose(machine)
            .doOnComplete { saveSearch(movieTitle) }

    private fun saveSearch(search: String) {
        if (search.isNotEmpty()) managerRepository.save(search)
    }
}
