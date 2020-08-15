package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.domain.services.CorMovieService
import com.meteoro.omdbarch.rest.util.CorInfrastructureRule
import com.meteoro.omdbarch.rest.util.assertErrorTransformed
import com.meteoro.omdbarch.rest.util.loadFile
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class CorMovieInfrastructureTest {

    @get:Rule
    val rule = CorInfrastructureRule()

    private lateinit var infrastructure: CorMovieService

    @Before
    fun `before each test`() {
        infrastructure = CorMovieInfrastructure(rule.api)
    }

    @Test
    fun `should handle result fetch movie`() {
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
            plot = "Earth's mightiest heroes must",
            language = "English, Russian, Hindi",
            country = "USA",
            awards = "Nominated for 1 Oscar. Another 38 wins & 79 nominations.",
            poster = "https://V1_SX1200.jpg",
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

        assertThat(simpleFetchMovie()).isEqualTo(movie)
    }

    @Test
    fun `should handle 200 but broken contract`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_movie_broken_contract.json")
        )

        assertErrorTransformed(
            whenRunning = this::simpleFetchMovie,
            expected = RemoteServiceIntegrationError.UnexpectedResponse
        )
    }

    @Test
    fun `should handle 404 not found`() {
        rule.defineScenario(
            status = 404,
            response = loadFile("404_not_found.json")
        )

        assertErrorTransformed(
            whenRunning = this::simpleFetchMovie,
            expected = RemoteServiceIntegrationError.ClientOrigin
        )
    }

    @Test
    fun `should handle 503 internal server error`() {
        rule.defineScenario(
            status = 503,
            response = loadFile("503_internal_server.json")
        )

        assertErrorTransformed(
            whenRunning = this::simpleFetchMovie,
            expected = RemoteServiceIntegrationError.RemoteSystem
        )
    }

    private fun simpleFetchMovie() = runBlocking {
        infrastructure.fetchMovie("10")
    }
}