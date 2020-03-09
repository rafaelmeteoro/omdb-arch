package com.meteoro.omdbarch.domain.repository

import com.meteoro.omdbarch.domain.model.ResultSearch
import io.reactivex.Flowable

interface SearchRepository {
    fun searchMovies(title: String, type: String? = null, year: String? = null): Flowable<ResultSearch>
}