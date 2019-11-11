package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.logger.ConsoleLogger
import com.meteoro.omdbarch.rest.util.InfrastructureRule
import com.meteoro.omdbarch.rest.util.loadFile
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SearchInfrastructureTests {

    @get:Rule
    val rule = InfrastructureRule()

    private lateinit var infrastructure: SearchInfrastructure

    @Before
    fun `before each test`() {
        infrastructure = SearchInfrastructure(
            service = rule.api,
            errorHandler = ExecutionErrorHandler(ConsoleLogger)
        )
    }

    @Test
    fun `should handle result search movies`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_search_with_result.json")
        )

        val search = ResultSearch(
            search = listOf(
                Movie(
                    title = "Avengers: Infinity War",
                    year = "2018",
                    imdbId = "tt4154756",
                    type = "movie",
                    poster = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_SX1200.jpg"
                ),
                Movie(
                    title = "Avengers: Endgame",
                    year = "2019",
                    imdbId = "tt4154796",
                    type = "movie",
                    poster = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_SX1200.jpg"
                )
            ),
            totalResults = 94,
            response = "True"
        )

        val testSubscriber = TestSubscriber<ResultSearch>()
        simpleSearchMovies()
            .subscribe(testSubscriber)

        testSubscriber.assertComplete()
            .assertTerminated()
            .assertNoErrors()
            .assertValue(search)
    }

    @Test
    fun `should handle 200 but broken contract`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_search_broken_contract.json")
        )

        val testSubscriber = TestSubscriber<ResultSearch>()
        simpleSearchMovies()
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

        val testSubscriber = TestSubscriber<ResultSearch>()
        simpleSearchMovies()
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

        val testSubscriber = TestSubscriber<ResultSearch>()
        simpleSearchMovies()
            .subscribe(testSubscriber)

        testSubscriber.assertNotComplete()
            .assertTerminated()
            .assertError(RemoteServiceIntegrationError.RemoteSystem)
    }

    private fun simpleSearchMovies() =
        infrastructure.searchMovies("Avengers", "", "")
}