package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.services.CorSearchService
import com.meteoro.omdbarch.rest.api.OmdbCoroutineAPI
import com.meteoro.omdbarch.rest.mapper.MapperResultSearch

class CorSearchInfrastructure(
    private val service: OmdbCoroutineAPI
) : CorSearchService {

    override suspend fun searchMovies(title: String, type: String?, year: String?): ResultSearch =
        managedExecution {
            val response = service.searchMovies(title, type, year)
            MapperResultSearch().fromResponse(response)
        }
}
