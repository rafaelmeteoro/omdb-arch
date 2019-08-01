package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.rest.util.InfrastructureRule
import com.meteoro.omdbarch.rest.util.loadFile
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class MoveisInfrastructureTests {

    @get:Rule
    val rule = InfrastructureRule()

    private lateinit var infrastructure: MoveisInfrastructure

    @Before
    fun `before each test`() {
        infrastructure = MoveisInfrastructure(rule.api)
    }

    @Test
    fun `should handle no results search movies`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_search_no_results.json")
        )

        val noMovies = emptyList<Movie>()

        val testObserver = TestObserver<List<Movie>>()
        simpleSearch().subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(noMovies)
    }

    @Test
    fun `should handle broken contract`() {
        rule.defineScenario(
            status = 200,
            response = loadFile("200_search_broken_contract.json")
        )

        val testObserver = TestObserver<List<Movie>>()
        simpleSearch().subscribe(testObserver)

        testObserver.assertNotComplete()
        testObserver.assertError(RemoteServiceIntegrationError.UnexpectedResponse)
    }

    private fun simpleSearch() =
        infrastructure.searchMovies("Avenges", "", "")
}