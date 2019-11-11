package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.rest.response.MovieResponse
import com.meteoro.omdbarch.rest.response.RatingResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `should map movie from response`() {
        val response = MovieResponse(
            title = "The Avengers",
            year = "2012",
            rated = "PG-13",
            released = "04 May 2012",
            runtime = "143 min",
            genre = "Action, Adventure, Sci-Fi",
            director = "Joss Whedon",
            writer = "Joss Whedon (screenplay), Zak Penn (story), Joss Whedon (story)",
            actors = "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
            plot = "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
            language = "English, Russian, Hindi",
            country = "USA",
            awards = "Nominated for 1 Oscar. Another 38 wins & 79 nominations.",
            poster = "https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
            ratings = listOf(
                RatingResponse(
                    source = "Internet Movie Database",
                    value = "8.1/10"
                ),
                RatingResponse(
                    source = "Rotten Tomatoes",
                    value = "92%"
                ),
                RatingResponse(
                    source = "Metacritic",
                    value = "69/100"
                )
            ),
            metascore = "69",
            imdbRating = "8.1",
            imdbVotes = "1,191,030",
            imdbId = "tt0848228",
            type = "movie",
            dvd = "25 Sep 2012",
            boxOffice = "$623,279,547",
            production = "Walt Disney Pictures",
            website = "http://marvel.com/avengers_movie",
            response = "True"
        )

        val expected = Movie(
            title = "The Avengers",
            year = "2012",
            rated = "PG-13",
            released = "04 May 2012",
            runtime = "143 min",
            genre = "Action, Adventure, Sci-Fi",
            director = "Joss Whedon",
            writer = "Joss Whedon (screenplay), Zak Penn (story), Joss Whedon (story)",
            actors = "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
            plot = "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
            language = "English, Russian, Hindi",
            country = "USA",
            awards = "Nominated for 1 Oscar. Another 38 wins & 79 nominations.",
            poster = "https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX1200.jpg",
            ratings = listOf(
                Rating(
                    source = "Internet Movie Database",
                    value = "8.1/10"
                ),
                Rating(
                    source = "Rotten Tomatoes",
                    value = "92%"
                ),
                Rating(
                    source = "Metacritic",
                    value = "69/100"
                )
            ),
            metascore = "69",
            imdbRating = "8.1",
            imdbVotes = "1,191,030",
            imdbId = "tt0848228",
            type = "movie",
            dvd = "25 Sep 2012",
            boxOffice = "$623,279,547",
            production = "Walt Disney Pictures",
            website = "http://marvel.com/avengers_movie",
            response = "True"
        )

        assertThat(MovieMapper(response)).isEqualTo(expected)
    }
}