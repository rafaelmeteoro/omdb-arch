package com.meteoro.omdbarch.persistance.realm

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRealm

object BuildMovieRealm {

    operator fun invoke(movie: Movie) =
        FavoriteMovieRealm(
            imdbId = movie.imdbId ?: "",
            title = movie.title,
            year = movie.year,
            released = movie.released,
            runtime = movie.runtime,
            genre = movie.genre,
            director = movie.director,
            actors = movie.actors,
            plot = movie.plot,
            country = movie.country,
            poster = movie.poster,
            imdbRating = movie.imdbRating,
            imdbVotes = movie.imdbVotes,
            type = movie.type
        )
}