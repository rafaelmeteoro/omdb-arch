package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.mapper.ResultSearchMapper
import com.meteoro.omdbarch.rest.response.SearchResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class SearchInfrastructure(
    private val service: OmdbAPI,
    private val errorHandler: ExecutionErrorHandler<SearchResponse>,
    private val errorHandlerFlowable: ExecutionErrorHandlerFlowable<SearchResponse>,
    private val targetScheduler: Scheduler = Schedulers.trampoline()
) : SearchService {

    override fun searchMovies(title: String, type: String?, year: String?): Observable<ResultSearch> {
        return service
            .searchMovies(title, type, year)
            .subscribeOn(targetScheduler)
            .compose(errorHandler)
            .map { ResultSearchMapper(it) }
    }

    override fun searchMovies(title: String): Flowable<ResultSearch> {
        return service
            .searchMovies(title)
            .subscribeOn(targetScheduler)
            .compose(errorHandlerFlowable)
            .map { ResultSearchMapper(it) }
    }
}