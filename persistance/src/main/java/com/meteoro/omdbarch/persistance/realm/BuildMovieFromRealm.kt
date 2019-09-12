package com.meteoro.omdbarch.persistance.realm

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRealm

object BuildMovieFromRealm {

    operator fun invoke(realm: FavoriteMovieRealm) =
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
}