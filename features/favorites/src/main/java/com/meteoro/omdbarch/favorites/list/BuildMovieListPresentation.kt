package com.meteoro.omdbarch.favorites.list

import com.meteoro.omdbarch.domain.model.Movie

object BuildMovieListPresentation {

    operator fun invoke(movies: List<Movie>) =
        MovieListPresentation(
            movies = movies
        )
}