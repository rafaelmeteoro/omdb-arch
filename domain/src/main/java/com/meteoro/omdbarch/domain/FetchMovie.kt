package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.services.MovieService
import io.reactivex.Observable

class FetchMovie(private val service: MovieService) {

    fun fetchMovie(id: String) =
        when {
            id.isEmpty() -> Observable.error(EmptyTerm)
            else -> service.fetchMovie(id)
        }
}