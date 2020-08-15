package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.coroutines.testutils.SuspendableErrorChecker.Companion.errorOnSuspendable
import com.meteoro.omdbarch.domain.errors.SearchMoviesError
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.repository.CorSearchRepository
import com.meteoro.omdbarch.domain.services.CorSearchService
import com.meteoro.omdbarch.domain.util.noResultSearch
import com.meteoro.omdbarch.domain.util.resultSearch
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class CorFetchSearchTest {

    private val service = mock<CorSearchService>()
    private lateinit var usecase: CorSearchRepository

    @Before
    fun `before each test`() {
        usecase = CorFetchSearch(service)
    }

    @Test
    fun `should throw with invalid term`() {
        errorOnSuspendable<ResultSearch> {
            take {
                noResultSearch
            }

            once {
                usecase.searchMovies("")
            }

            check { error ->
                assertThat(error).isEqualTo(SearchMoviesError.EmptyTerm)
            }
        }
    }

    @Test
    fun `should throw with empty result`() {
        errorOnSuspendable<ResultSearch> {
            take {
                noResultSearch
            }

            once { result ->
                whenever(service.searchMovies(any(), any(), any())).thenReturn(result)
                usecase.searchMovies("Avengers", "", "")
            }

            check { error ->
                assertThat(error).isEqualTo(SearchMoviesError.NoResultsFound)
            }
        }
    }

    @Test
    fun `should search valid title`() {
        runBlocking {
            whenever(service.searchMovies(any(), any(), any())).thenReturn(resultSearch)
            assertThat(usecase.searchMovies("Avengers", "", "")).isEqualTo(resultSearch)
        }
    }
}
