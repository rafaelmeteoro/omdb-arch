package com.meteoro.omdbarch.persistance.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class FavoriteMovieRoom(
    @PrimaryKey
    val imdbId: String,
    val title: String? = null,
    val year: String? = null,
    val released: String? = null,
    val runtime: String? = null,
    val genre: String? = null,
    val director: String? = null,
    val actors: String? = null,
    val plot: String? = null,
    val country: String? = null,
    val poster: String? = null,
    val imdbRating: String? = null,
    val imdbVotes: String? = null,
    val type: String? = null
)