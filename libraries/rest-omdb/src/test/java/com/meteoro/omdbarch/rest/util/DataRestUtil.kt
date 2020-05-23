package com.meteoro.omdbarch.rest.util

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.rest.response.MovieResponse
import com.meteoro.omdbarch.rest.response.RatingResponse

object DataRestUtil {

    private val ratingResponse by lazy {
        RatingResponse(
            source = "source",
            value = "value"
        )
    }

    private val rating by lazy {
        Rating(
            source = "source",
            value = "value"
        )
    }

    val movieResponse by lazy {
        MovieResponse(
            title = "Title",
            year = "Year",
            rated = "Rated",
            released = "Released",
            runtime = "Runtime",
            genre = "Genre",
            director = "Director",
            writer = "Writer",
            actors = "Actors",
            plot = "Plot",
            language = "Language",
            country = "Country",
            awards = "Awards",
            poster = "https://V1_SX300.jpg",
            ratings = listOf(ratingResponse),
            metascore = "Metascore",
            imdbRating = "ImdbRating",
            imdbVotes = "ImdbVotes",
            imdbId = "ImdbId",
            type = "Type",
            dvd = "DVD",
            boxOffice = "BoxOffice",
            production = "Production",
            website = "Website",
            response = "Response"
        )
    }

    val movie by lazy {
        Movie(
            title = "Title",
            year = "Year",
            rated = "Rated",
            released = "Released",
            runtime = "Runtime",
            genre = "Genre",
            director = "Director",
            writer = "Writer",
            actors = "Actors",
            plot = "Plot",
            language = "Language",
            country = "Country",
            awards = "Awards",
            poster = "https://V1_SX1200.jpg",
            ratings = listOf(rating),
            metascore = "Metascore",
            imdbRating = "ImdbRating",
            imdbVotes = "ImdbVotes",
            imdbId = "ImdbId",
            type = "Type",
            dvd = "DVD",
            boxOffice = "BoxOffice",
            production = "Production",
            website = "Website",
            response = "Response"
        )
    }
}
