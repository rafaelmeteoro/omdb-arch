package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.domain.services.ConnectivityService
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.logger.ConsoleLogger
import com.meteoro.omdbarch.rest.executor.RemoteExecutor
import com.meteoro.omdbarch.rest.executor.RemoteExecutorImpl
import com.meteoro.omdbarch.rest.util.InfrastructureRule
import com.meteoro.omdbarch.rest.util.loadFile
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class MovieInfrastructureTest {

    @get:Rule
    val rule = InfrastructureRule()

    private val service = mock<ConnectivityService>()
    private lateinit var executor: RemoteExecutor
    private lateinit var infrastructure: MovieService

    @Before
    fun `before each test`() {
        executor = RemoteExecutorImpl(service)
        infrastructure = MovieInfrastructure(
            service = rule.api,
            executor = executor,
            errorHandler = ExecutionErrorHandler(ConsoleLogger)
        )

        whenever(service.isConnected()).thenReturn(true)
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

        val testSubscriber = TestSubscriber<Movie>()
        simpleFetchMovie()
            .subscribe(testSubscriber)

        testSubscriber.assertComplete()
            .assertTerminated()
            .assertNoErrors()
            .assertValue(movie)
    }

    @Test
    fun `should handle 200 but broken contract`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_movie_broken_contract.json")
        )

        val testSubscriber = TestSubscriber<Movie>()
        simpleFetchMovie()
            .subscribe(testSubscriber)

        testSubscriber.assertNotComplete()
            .assertTerminated()
            .assertError(RemoteServiceIntegrationError.UnexpectedResponse)
    }

    @Test
    fun `should handle 404 not found`() {
        rule.defineScenario(
            status = 404,
            response = loadFile("404_not_found.json")
        )

        val testSubscriber = TestSubscriber<Movie>()
        simpleFetchMovie()
            .subscribe(testSubscriber)

        testSubscriber.assertNotComplete()
            .assertTerminated()
            .assertError(RemoteServiceIntegrationError.ClientOrigin)
    }

    @Test
    fun `should handle 503 internal server error`() {
        rule.defineScenario(
            status = 503,
            response = loadFile("503_internal_server.json")
        )

        val testSubscriber = TestSubscriber<Movie>()
        simpleFetchMovie()
            .subscribe(testSubscriber)

        testSubscriber.assertNotComplete()
            .assertTerminated()
            .assertError(RemoteServiceIntegrationError.RemoteSystem)
    }

    @Test
    fun `should handle no internet connection`() {
        whenever(service.isConnected()).thenReturn(false)

        val testSubscriber = TestSubscriber<Movie>()
        simpleFetchMovie()
            .subscribe(testSubscriber)

        testSubscriber.assertNotComplete()
            .assertTerminated()
            .assertError(NetworkingError.NoInternetConnection)
    }

    private fun simpleFetchMovie() =
        infrastructure.fetchMovie("10")
}
