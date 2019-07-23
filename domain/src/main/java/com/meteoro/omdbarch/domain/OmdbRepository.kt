package com.meteoro.omdbarch.domain

interface OmdbRepository {
    fun searchMovies(
        title: String,
        type: String? = null,
        year: String? = null
    )

    fun getMovie(
        id: String
    )
}