package com.meteoro.omdbarch.domain.errors

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class SearchMoviesErrorTest {

    @Test
    fun `should be empty term`() {
        val error: SearchMoviesError = SearchMoviesError.EmptyTerm
        val message = "Search term can not be empty"

        assertThat(error.toString()).isEqualTo(message)
    }

    @Test
    fun `should be no results found`() {
        val error: SearchMoviesError = SearchMoviesError.NoResultsFound
        val message = "No found for this search"

        assertThat(error.toString()).isEqualTo(message)
    }
}
