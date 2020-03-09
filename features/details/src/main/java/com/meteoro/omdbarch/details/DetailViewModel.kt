package com.meteoro.omdbarch.details

import com.meteoro.omdbarch.components.ResourceProvider
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.repository.MovieRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import io.reactivex.Flowable

class DetailViewModel(
    private val movieRepository: MovieRepository,
    private val cacheRepository: CacheRepository,
    private val machine: StateMachine<MovieDetailPresentation>,
    private val resProvider: ResourceProvider
) : DetailViewModelContract {

    override fun fetchMovie(idImdb: String): Flowable<ViewState<MovieDetailPresentation>> =
        movieRepository.fetchMovie(idImdb)
            .flatMap { movie ->
                saveMovie(movie)
                Flowable.just(movie)
            }
            .map { MovieDetailPresentationMapper().fromDomain(it, resProvider) }
            .compose(machine)

    private fun saveMovie(movie: Movie) {
        cacheRepository.saveMovie(movie)
    }
}