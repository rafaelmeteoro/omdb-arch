package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.ResultSearch

interface CorSearchService {
    suspend fun searchMovies(title: String, type: String? = null, year: String? = null): ResultSearch
}
