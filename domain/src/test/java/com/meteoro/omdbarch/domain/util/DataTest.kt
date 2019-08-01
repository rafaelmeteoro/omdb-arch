package com.meteoro.omdbarch.domain.util

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch

val movie by lazy {
    Movie(
        title = "Movie"
    )
}

val resultSearch by lazy {
    ResultSearch(
        search = listOf(movie),
        totalResults = 1,
        response = "True"
    )
}

val noResultSearch by lazy {
    ResultSearch(
        response = "False"
    )
}