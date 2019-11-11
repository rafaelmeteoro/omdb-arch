package com.meteoro.omdbarch.rest.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("Title") val title: String,
    @SerialName("Year") val year: String,
    @SerialName("Rated") val rated: String? = null,
    @SerialName("Released") val released: String? = null,
    @SerialName("Runtime") val runtime: String? = null,
    @SerialName("Genre") val genre: String? = null,
    @SerialName("Director") val director: String? = null,
    @SerialName("Writer") val writer: String? = null,
    @SerialName("Actors") val actors: String? = null,
    @SerialName("Plot") val plot: String? = null,
    @SerialName("Language") val language: String? = null,
    @SerialName("Country") val country: String? = null,
    @SerialName("Awards") val awards: String? = null,
    @SerialName("Poster") val poster: String,
    @SerialName("Ratings") val ratings: List<RatingResponse>? = null,
    @SerialName("Metascore") val metascore: String? = null,
    @SerialName("imdbRating") val imdbRating: String? = null,
    @SerialName("imdbVotes") val imdbVotes: String? = null,
    @SerialName("imdbID") val imdbId: String,
    @SerialName("Type") val type: String,
    @SerialName("DVD") val dvd: String? = null,
    @SerialName("BoxOffice") val boxOffice: String? = null,
    @SerialName("Production") val production: String? = null,
    @SerialName("Website") val website: String? = null,
    @SerialName("Response") val response: String? = null
)