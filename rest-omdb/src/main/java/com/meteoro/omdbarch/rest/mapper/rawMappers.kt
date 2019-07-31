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

internal fun MovieResponse.asMovie() =
    Movie(
        title,
        year,
        rated,
        released,
        runtime,
        genre,
        director,
        writer,
        actors,
        plot,
        language,
        country,
        awards,
        poster?.replace("300", "1200"),
        ratings?.asRatings(),
        metascore,
        imdbRating,
        imdbVotes,
        imdbId,
        type,
        dvd,
        boxOffice,
        production,
        website,
        response
    )

internal fun List<MovieResponse>.asMovies() =
    if (isEmpty()) emptyList()
    else this.map {
        it.asMovie()
    }

internal fun SearchResponse.asMovies() =
    this.search?.asMovies() ?: emptyList()
