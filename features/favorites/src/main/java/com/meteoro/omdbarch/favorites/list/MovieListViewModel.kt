package com.meteoro.omdbarch.favorites.list

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import io.reactivex.Flowable

class MovieListViewModel(
    private val cacheRepository: CacheRepository,
    private val machine: StateMachine<MovieListPresentation>
) {
    fun fetchMoviesSaved(): Flowable<ViewState<MovieListPresentation>> =
        cacheRepository.getMovies()
            .map { MovieListPresentationMapper().fromDomain(it) }
            .compose(machine)

    fun deleteMovie(movie: Movie) {
        cacheRepository.deleteMovie(movie)
    }

    fun deleteAll() {
        cacheRepository.deleteAll()
    }
}