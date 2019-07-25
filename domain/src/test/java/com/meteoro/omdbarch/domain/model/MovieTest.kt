package com.meteoro.omdbarch.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MovieTest {

    @Test
    fun `test values movie`() {
        val sourceExpected = "source"
        val valueExpected = "value"
        val ratingsExpected = listOf<Rating>(
            Rating(sourceExpected, valueExpected)
        )

        val titleExpected = "title"
        val yearExpected = "year"
        val ratedExpected = "rated"
        val releasedExpected = "released"
        val runtimeExpected = "runtime"
        val genreExpected = "genre"
        val directorExpected = "director"
        val writerExpected = "writer"
        val actorsExpected = "actors"
        val plotExpected = "plot"
        val languageExpected = "language"
        val countryExpected = "country"
        val awardsExpected = "awards"
        val posterExpected = "poster"
        val metascoreExpected = "metascore"
        val imdbRatingExpected = "imdbRating"
        val imdbVotesExpected = "imdbVotes"
        val imdbIdExpected = "imdbId"
        val typeExpected = "type"
        val dvdExpected = "dvd"
        val boxOfficeExpected = "boxOffice"
        val productionExpected = "production"
        val websiteExpected = "website"
        val responseExpected = "response"

        val movie = Movie(
            titleExpected,
            yearExpected,
            ratedExpected,
            releasedExpected,
            runtimeExpected,
            genreExpected,
            directorExpected,
            writerExpected,
            actorsExpected,
            plotExpected,
            languageExpected,
            countryExpected,
            awardsExpected,
            posterExpected,
            ratingsExpected,
            metascoreExpected,
            imdbRatingExpected,
            imdbVotesExpected,
            imdbIdExpected,
            typeExpected,
            dvdExpected,
            boxOfficeExpected,
            productionExpected,
            websiteExpected,
            responseExpected
        )

        assertThat(movie.title).isEqualTo(titleExpected)
        assertThat(movie.year).isEqualTo(yearExpected)
        assertThat(movie.rated).isEqualTo(ratedExpected)
        assertThat(movie.released).isEqualTo(releasedExpected)
        assertThat(movie.runtime).isEqualTo(runtimeExpected)
        assertThat(movie.genre).isEqualTo(genreExpected)
        assertThat(movie.director).isEqualTo(directorExpected)
        assertThat(movie.writer).isEqualTo(writerExpected)
        assertThat(movie.actors).isEqualTo(actorsExpected)
        assertThat(movie.plot).isEqualTo(plotExpected)
        assertThat(movie.language).isEqualTo(languageExpected)
        assertThat(movie.country).isEqualTo(countryExpected)
        assertThat(movie.awards).isEqualTo(awardsExpected)
        assertThat(movie.poster).isEqualTo(posterExpected)
        assertThat(movie.metascore).isEqualTo(metascoreExpected)
        assertThat(movie.imdbRating).isEqualTo(imdbRatingExpected)
        assertThat(movie.imdbVotes).isEqualTo(imdbVotesExpected)
        assertThat(movie.imdbId).isEqualTo(imdbIdExpected)
        assertThat(movie.type).isEqualTo(typeExpected)
        assertThat(movie.dvd).isEqualTo(dvdExpected)
        assertThat(movie.boxOffice).isEqualTo(boxOfficeExpected)
        assertThat(movie.production).isEqualTo(productionExpected)
        assertThat(movie.website).isEqualTo(websiteExpected)
        assertThat(movie.response).isEqualTo(responseExpected)
        movie.ratings?.forEachIndexed { index, rating ->
            val ratingExpected = ratingsExpected[index]
            assertThat(rating).isEqualTo(ratingExpected)
        }
    }
}