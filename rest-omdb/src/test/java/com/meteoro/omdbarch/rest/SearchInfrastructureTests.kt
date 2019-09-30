package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.*
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.logger.ConsoleLogger
import com.meteoro.omdbarch.rest.util.InfrastructureRule
import com.meteoro.omdbarch.rest.util.loadFile
import io.reactivex.observers.TestObserver
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
            errorHandler = ExecutionErrorHandler(ConsoleLogger),
            errorHandlerFlowable = ExecutionErrorHandlerFlowable(ConsoleLogger)
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

        val testObserver = TestObserver<ResultSearch>()
        simpleSearchMovies()
            .subscribe(testObserver)

        testObserver.assertComplete()
            .assertTerminated()
            .assertNoErrors()
            .assertValue(search)
    }

    @Test
    fun `should handle result search movies flowable`() {
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

        simpleSearchMoviesFlowable().test()
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

        val testObserver = TestObserver<ResultSearch>()
        simpleSearchMovies()
            .subscribe(testObserver)

        testObserver.assertNotComplete()
            .assertTerminated()
            .assertError(UnexpectedResponse)
    }

    @Test
    fun `should handle 200 but broken contract flowable`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_search_broken_contract.json")
        )

        simpleSearchMoviesFlowable().test()
            .assertTerminated()
            .assertError(UnexpectedResponse)
    }

    @Test
    fun `should handle 404 not found`() {
        rule.defineScenario(
            status = 404,
            response = loadFile("404_not_found.json")
        )

        val testObserver = TestObserver<ResultSearch>()
        simpleSearchMovies()
            .subscribe(testObserver)

        testObserver.assertNotComplete()
            .assertTerminated()
            .assertError(ClientOrigin)
    }

    @Test
    fun `should handle 404 not found flowable`() {
        rule.defineScenario(
            status = 404,
            response = loadFile("404_not_found.json")
        )

        simpleSearchMoviesFlowable().test()
            .assertTerminated()
            .assertError(ClientOrigin)
    }

    @Test
    fun `should handle 503 internal server error`() {
        rule.defineScenario(
            status = 503,
            response = loadFile("503_internal_server.json")
        )

        val testObserver = TestObserver<ResultSearch>()
        simpleSearchMovies()
            .subscribe(testObserver)

        testObserver.assertNotComplete()
            .assertTerminated()
            .assertError(RemoteSystem)
    }

    @Test
    fun `should handle 503 internal server error flowable`() {
        rule.defineScenario(
            status = 503,
            response = loadFile("503_internal_server.json")
        )

        simpleSearchMoviesFlowable().test()
            .assertTerminated()
            .assertError(RemoteSystem)
    }

    private fun simpleSearchMovies() =
        infrastructure.searchMovies("Avengers", "", "")

    private fun simpleSearchMoviesFlowable() =
        infrastructure.searchMovies("Avengers")
}