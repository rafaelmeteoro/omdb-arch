package com.meteoro.omdbarch.details

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.utilities.ResourceProvider

object BuildMovieDetailPresentation {

    operator fun invoke(info: Movie, resProvider: ResourceProvider) =
        MovieDetailPresentation(
            title = info.title ?: defaultTitle,
            year = info.year ?: defaultYear,
            rating = resProvider.getString(R.string.imdb_rating_label, info.imdbRating ?: defaultFormat),
            cast = resProvider.getString(R.string.cast, info.actors ?: defaultFormat),
            directors = resProvider.getString(R.string.directors, info.director ?: defaultFormat),
            plot = resProvider.getString(R.string.plot, info.plot ?: defaultFormat),
            poster = info.poster ?: defaultPoster
        )

    private const val defaultTitle = "No Title"
    private const val defaultYear = "????"
    private const val defaultFormat = "-"
    private const val defaultPoster = ""
}