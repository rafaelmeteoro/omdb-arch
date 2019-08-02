package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.rest.response.MovieResponse

object MovieMapper {

    operator fun invoke(responseApi: MovieResponse) = with(responseApi) {
        Movie(
            title = title,
            year = year,
            rated = rated,
            released = released,
            runtime = runtime,
            genre = genre,
            director = director,
            writer = writer,
            actors = actors,
            plot = plot,
            language = language,
            country = country,
            awards = awards,
            poster = poster.replace("300", "1200"),
            ratings = ratings?.map { RatingMapper(it) },
            metascore = metascore,
            imdbRating = imdbRating,
            imdbVotes = imdbVotes,
            imdbId = imdbId,
            type = type,
            dvd = dvd,
            boxOffice = boxOffice,
            production = production,
            website = website,
            response = response
        )
    }
}