package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.mapper.MapperMovie
import com.meteoro.omdbarch.rest.response.MovieResponse
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class MovieInfrastructure(
    private val service: OmdbAPI,
    private val errorHandler: ExecutionErrorHandler<MovieResponse>,
    private val targetScheduler: Scheduler = Schedulers.trampoline()
) : MovieService {

    override fun fetchMovie(id: String): Flowable<Movie> =
        service
            .fetchMovie(id)
            .subscribeOn(targetScheduler)
            .compose(errorHandler)
            .map { MapperMovie().fromResponse(it) }
}