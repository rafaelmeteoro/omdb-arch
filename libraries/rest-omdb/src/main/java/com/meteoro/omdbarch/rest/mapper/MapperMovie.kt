package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.rest.response.MovieResponse

class MapperMovie {

    fun fromResponse(response: MovieResponse): Movie =
        Movie(
            title = response.title,
            year = response.year,
            rated = response.rated,
            released = response.released,
            runtime = response.runtime,
            genre = response.genre,
            director = response.director,
            writer = response.writer,
            actors = response.actors,
            plot = response.plot,
            language = response.language,
            country = response.country,
            awards = response.awards,
            poster = response.poster.replace("300", "1200"),
            ratings = response.ratings?.map { MapperRating().fromResponse(it) },
            metascore = response.metascore,
            imdbRating = response.imdbRating,
            imdbVotes = response.imdbVotes,
            imdbId = response.imdbId,
            type = response.type,
            dvd = response.dvd,
            boxOffice = response.boxOffice,
            production = response.production,
            website = response.website,
            response = response.response
        )
}