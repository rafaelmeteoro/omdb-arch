package com.meteoro.omdbarch.persistance.room

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom

object BuildMovieFromRoom {

    operator fun invoke(room: FavoriteMovieRoom) =
        Movie(
            imdbId = room.imdbId,
            title = room.title,
            year = room.year,
            released = room.released,
            runtime = room.runtime,
            genre = room.genre,
            director = room.director,
            actors = room.actors,
            plot = room.plot,
            country = room.country,
            poster = room.poster,
            imdbRating = room.imdbRating,
            imdbVotes = room.imdbVotes,
            type = room.type
        )
}