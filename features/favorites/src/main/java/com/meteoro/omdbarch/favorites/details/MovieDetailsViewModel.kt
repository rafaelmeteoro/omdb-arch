package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import io.reactivex.Flowable

class MovieDetailsViewModel(
    private val cacheRepository: CacheRepository,
    private val machine: StateMachine<MovieDetailsPresentation>
) {
    fun fetchMovieSaved(imdbId: String): Flowable<ViewState<MovieDetailsPresentation>> =
        cacheRepository.getMovie(imdbId)
            .map { MovieDetailsPresentationMapper().fromDomain(it) }
            .compose(machine)
}