package com.meteoro.omdbarch.domain.errors

sealed class SearchMoviesError : Throwable() {

    object EmptyTerm : SearchMoviesError()
    object NoResultsFound : SearchMoviesError()

    override fun toString() = when (this) {
        EmptyTerm -> "Search term can not be empty"
        NoResultsFound -> "No found for this search"
    }
}