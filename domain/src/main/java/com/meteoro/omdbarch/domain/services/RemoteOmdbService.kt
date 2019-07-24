package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.Movie

interface RemoteOmdbService {
    fun searchMovies(
        title: String,
        type: String? = null,
        year: String? = null
    ): List<Movie>

    fun fetchMovie(id: String): Movie
}