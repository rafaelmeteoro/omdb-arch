package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.services.CorSearchService
import com.meteoro.omdbarch.rest.util.CorInfrastructureRule
import com.meteoro.omdbarch.rest.util.assertErrorTransformed
import com.meteoro.omdbarch.rest.util.loadFile
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class CorSearchInfrastructureTest {

    @get:Rule
    val rule = CorInfrastructureRule()

    private lateinit var infrastructure: CorSearchService

    @Before
    fun `before each test`() {
        infrastructure = CorSearchInfrastructure(
            service = rule.api
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
                    poster = "https://V1_SX1200.jpg"
                ),
                Movie(
                    title = "Avengers: Endgame",
                    year = "2019",
                    imdbId = "tt4154796",
                    type = "movie",
                    poster = "https://V1_SX1200.jpg"
                )
            ),
            totalResults = 94,
            response = "True"
        )

        assertThat(simpleSearchMovies()).isEqualTo(search)
    }

    @Test
    fun `should handle 200 but broken contract`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_search_broken_contract.json")
        )

        assertErrorTransformed(
            whenRunning = this::simpleSearchMovies,
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
            whenRunning = this::simpleSearchMovies,
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
            whenRunning = this::simpleSearchMovies,
            expected = RemoteServiceIntegrationError.RemoteSystem
        )
    }

    private fun simpleSearchMovies() = runBlocking {
        infrastructure.searchMovies("Avengers", "", "")
    }
}
