package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.CorMovieService
import com.meteoro.omdbarch.rest.api.OmdbCoroutineAPI
import com.meteoro.omdbarch.rest.mapper.MapperMovie

class CorMovieInfrastructure(
    private val service: OmdbCoroutineAPI
) : CorMovieService {

    override suspend fun fetchMovie(id: String): Movie =
        managedExecution {
            val response = service.fetchMovie(id)
            MapperMovie().fromResponse(response)
        }
}