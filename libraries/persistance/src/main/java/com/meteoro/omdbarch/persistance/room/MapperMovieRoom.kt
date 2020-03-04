package com.meteoro.omdbarch.persistance.room

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom

class MapperMovieRoom {

    fun fromRoom(room: FavoriteMovieRoom): Movie =
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

    fun fromMovie(movie: Movie): FavoriteMovieRoom =
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