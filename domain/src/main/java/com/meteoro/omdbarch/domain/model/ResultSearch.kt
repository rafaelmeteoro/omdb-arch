package com.meteoro.omdbarch.domain.model

data class ResultSearch(
    val search: List<Movie>? = null,
    val totalResults: Int = 0,
    val response: String? = null
)