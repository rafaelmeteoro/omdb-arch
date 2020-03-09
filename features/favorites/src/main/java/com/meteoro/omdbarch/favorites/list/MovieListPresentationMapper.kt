package com.meteoro.omdbarch.favorites.list

import com.meteoro.omdbarch.domain.model.Movie

class MovieListPresentationMapper {

    fun fromDomain(movies: List<Movie>): MovieListPresentation =
        MovieListPresentation(
            movies = movies
        )
}