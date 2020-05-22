package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.domain.model.TypeMovie

data class HomePresentation(
    val movies: List<MoviePresentation>,
    val response: String,
    val totalResults: Int
)

data class MoviePresentation(
    val imdbId: String,
    val title: String,
    val photoUrl: String,
    val type: TypeMovie
)
