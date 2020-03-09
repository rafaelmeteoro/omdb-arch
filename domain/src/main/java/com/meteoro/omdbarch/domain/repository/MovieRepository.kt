package com.meteoro.omdbarch.domain.repository

import com.meteoro.omdbarch.domain.model.Movie
import io.reactivex.Flowable

interface MovieRepository {
    fun fetchMovie(id: String): Flowable<Movie>
}