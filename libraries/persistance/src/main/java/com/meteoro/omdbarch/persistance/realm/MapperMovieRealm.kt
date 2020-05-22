package com.meteoro.omdbarch.persistance.realm

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRealm

class MapperMovieRealm {

    fun fromRealm(realm: FavoriteMovieRealm): Movie =
        Movie(
            imdbId = realm.imdbId,
            title = realm.title,
            year = realm.year,
            released = realm.released,
            runtime = realm.runtime,
            genre = realm.genre,
            director = realm.director,
            actors = realm.actors,
            plot = realm.plot,
            country = realm.country,
            poster = realm.poster,
            imdbRating = realm.imdbRating,
            imdbVotes = realm.imdbVotes,
            type = realm.type
        )

    fun fromMovie(movie: Movie): FavoriteMovieRealm =
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
