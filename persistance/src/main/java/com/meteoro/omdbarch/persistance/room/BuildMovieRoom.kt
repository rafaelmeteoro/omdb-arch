package com.meteoro.omdbarch.persistance.room

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom

object BuildMovieRoom {

    operator fun invoke(movie: Movie) =
        FavoriteMovieRoom(
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