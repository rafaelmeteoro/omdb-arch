package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.ResultSearch
import io.reactivex.Flowable

interface SearchService {
    fun searchMovies(title: String, type: String? = null, year: String? = null): Flowable<ResultSearch>
}