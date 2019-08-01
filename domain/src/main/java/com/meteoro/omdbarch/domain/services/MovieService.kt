package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.Movie
import io.reactivex.Observable

interface MovieService {
    fun fetchMovie(id: String): Observable<Movie>
}