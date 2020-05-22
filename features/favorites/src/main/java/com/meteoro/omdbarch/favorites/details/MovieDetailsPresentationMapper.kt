package com.meteoro.omdbarch.favorites.details

import com.meteoro.omdbarch.domain.model.Movie

class MovieDetailsPresentationMapper {

    fun fromDomain(movie: Movie): MovieDetailsPresentation =
        MovieDetailsPresentation(
            movie = movie
        )
}
