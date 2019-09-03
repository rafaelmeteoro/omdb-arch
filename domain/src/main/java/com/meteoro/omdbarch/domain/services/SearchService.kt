package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.ResultSearch
import io.reactivex.Observable

interface SearchService {
    fun searchMovies(title: String, type: String? = null, year: String? = null): Observable<ResultSearch>
}