package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.domain.model.Movie

object BuildMovieDetailsPresentation {

    operator fun invoke(movie: Movie) =
        MovieDetailsPresentation(
            movie = movie
        )
}