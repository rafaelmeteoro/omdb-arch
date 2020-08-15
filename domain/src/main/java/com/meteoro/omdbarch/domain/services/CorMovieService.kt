package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.Movie

interface CorMovieService {
    suspend fun fetchMovie(id: String): Movie
}
