package com.meteoro.omdbarch.home

data class HomePresentation(
    val movies: List<MoviePresentation>,
    val response: String,
    val totalResults: Int
)

data class MoviePresentation(
    val imdbId: String,
    val title: String,
    val photoUrl: String
)