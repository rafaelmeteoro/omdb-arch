package com.meteoro.omdbarch.favorites.list

import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import io.reactivex.Flowable

class MovieListViewModel(
    private val cache: CacheMovie,
    private val machine: StateMachine<MovieListPresentation>
) {
    fun fetchMoviesSaved(): Flowable<ViewState<MovieListPresentation>> =
        cache.getMovies()
            .map { BuildMovieListPresentation(it) }
            .compose(machine)

    fun deleteMovie(movie: Movie) {
        cache.deleteMovie(movie)
    }

    fun deleteAll() {
        cache.deleteAll()
    }
}