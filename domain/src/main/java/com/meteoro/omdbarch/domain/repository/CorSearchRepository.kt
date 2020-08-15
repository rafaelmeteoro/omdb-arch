package com.meteoro.omdbarch.domain.repository

import com.meteoro.omdbarch.domain.model.ResultSearch

interface CorSearchRepository {
    suspend fun searchMovies(title: String, type: String? = null, year: String? = null): ResultSearch
}