package com.meteoro.omdbarch.domain.services

import com.meteoro.omdbarch.domain.model.Movie
import io.reactivex.Flowable

interface MovieService {
    fun fetchMovie(id: String): Flowable<Movie>
}