package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.rest.response.MovieResponse
import com.meteoro.omdbarch.rest.response.RatingResponse
import com.meteoro.omdbarch.rest.response.SearchResponse

internal fun List<RatingResponse>.asRatings() =
    if (isEmpty()) emptyList()
    else this.map {
        Rating(it.source, it.value)
    }

internal fun List<MovieResponse>.asMovies() =
    if (isEmpty()) emptyList()
    else this.map {
        Movie(
            it.title,
            it.year,
            it.rated,
            it.released,
            it.runtime,
            it.genre,
            it.director,
            it.writer,
            it.actors,
            it.plot,
            it.language,
            it.country,
            it.awards,
            it.poster?.replace("300", "1200"),
            it.ratings?.asRatings(),
            it.metascore,
            it.imdbRating,
            it.imdbVotes,
            it.imdbId,
            it.type,
            it.dvd,
            it.boxOffice,
            it.production,
            it.website,
            it.response
        )
    }

internal fun SearchResponse.asMovies() =
    this.search?.asMovies() ?: emptyList()
