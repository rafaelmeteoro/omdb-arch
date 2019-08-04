package com.meteoro.omdbarch.features.details

data class MovieDetailPresentation(
    val title: String,
    val year: String,
    val rating: String,
    val cast: String,
    val directors: String,
    val plot: String,
    val poster: String
)