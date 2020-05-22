package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.repository.MovieRepository
import com.meteoro.omdbarch.domain.services.MovieService
import io.reactivex.Flowable

class FetchMovie(private val service: MovieService) : MovieRepository {

    override fun fetchMovie(id: String): Flowable<Movie> =
        when {
            id.isEmpty() -> Flowable.error(EmptyTerm)
            else -> service.fetchMovie(id)
        }
}
