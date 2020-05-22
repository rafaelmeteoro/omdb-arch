package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.executor.RemoteExecutor
import com.meteoro.omdbarch.rest.mapper.MapperResultSearch
import com.meteoro.omdbarch.rest.response.SearchResponse
import io.reactivex.Flowable

class SearchInfrastructure(
    private val service: OmdbAPI,
    private val executor: RemoteExecutor,
    private val errorHandler: ExecutionErrorHandler<SearchResponse>
) : SearchService {

    override fun searchMovies(title: String, type: String?, year: String?): Flowable<ResultSearch> =
        executor.checkConnectionAndThanFlowable(
            action = service
                .searchMovies(title, type, year)
                .compose(errorHandler)
                .map { MapperResultSearch().fromResponse(it) }
        )
}
