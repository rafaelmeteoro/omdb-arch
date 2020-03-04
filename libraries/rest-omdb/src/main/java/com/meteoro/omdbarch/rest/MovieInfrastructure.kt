package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.executor.RemoteExecutor
import com.meteoro.omdbarch.rest.mapper.MapperMovie
import com.meteoro.omdbarch.rest.response.MovieResponse
import io.reactivex.Flowable

class MovieInfrastructure(
    private val service: OmdbAPI,
    private val executor: RemoteExecutor,
    private val errorHandler: ExecutionErrorHandler<MovieResponse>
) : MovieService {

    override fun fetchMovie(id: String): Flowable<Movie> =
        executor.checkConnectionAndThanFlowable(
            action = service
                .fetchMovie(id)
                .compose(errorHandler)
                .map { MapperMovie().fromResponse(it) }
        )
}