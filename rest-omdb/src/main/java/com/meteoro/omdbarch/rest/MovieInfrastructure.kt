package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.mapper.MovieMapper
import com.meteoro.omdbarch.rest.response.MovieResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class MovieInfrastructure(
    private val service: OmdbAPI,
    private val errorHandler: ExecutionErrorHandler<MovieResponse>,
    private val targetScheduler: Scheduler = Schedulers.trampoline()
) : MovieService {

    override fun fetchMovie(id: String): Observable<Movie> {
        return service
            .fetchMovie(id)
            .subscribeOn(targetScheduler)
            .compose(errorHandler)
            .map { MovieMapper(it) }
    }
}