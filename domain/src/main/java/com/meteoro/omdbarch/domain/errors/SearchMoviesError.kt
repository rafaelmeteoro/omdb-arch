package com.meteoro.omdbarch.domain.errors

sealed class SearchMoviesError(message: String) : Throwable(message) {
    object EmptyTerm : SearchMoviesError("Search term can not be empty")
    object NoResultsFound : SearchMoviesError("No found for this search")
}