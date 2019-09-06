package com.meteoro.omdbarch.domain.model

enum class TypeMovie(val type: String) {
    MOVIE(type = "movie"),
    SERIES(type = "series"),
    OUTRO(type = "outro");

    companion object {
        private val map = values().associateBy(TypeMovie::type)
        fun fromString(type: String) = map[type]
    }
}