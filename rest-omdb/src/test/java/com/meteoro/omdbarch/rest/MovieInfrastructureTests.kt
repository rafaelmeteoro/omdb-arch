package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.logger.ConsoleLogger
import com.meteoro.omdbarch.rest.util.InfrastructureRule
import com.meteoro.omdbarch.rest.util.loadFile
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class MovieInfrastructureTests {

    @get:Rule
    val rule = InfrastructureRule()

    private lateinit var infrastructure: MovieInfrastructure

    @Before
    fun `before each test`() {
        infrastructure = MovieInfrastructure(
            service = rule.api,
            errorHandler = ExecutionErrorHandler(ConsoleLogger)
        )
    }

    @Test
    fun `should handle no results search movies`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_movie_with_result.json")
        )

        val movie = Movie(
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

        val testObserver = TestObserver<Movie>()
        infrastructure.fetchMovie("Avenges")
            .subscribe(testObserver)

        testObserver.assertComplete()
            .assertTerminated()
            .assertNoErrors()
            .assertValue(movie)
    }
}