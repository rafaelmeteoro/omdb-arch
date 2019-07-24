package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class FetchMoviesTests {

    private val omdbService = mock<RemoteOmdbService>()
    private lateinit var usecase: FetchMovies

    private val movies by lazy {
        listOf(
            Movie(
                title = "Movie title",
                year = "2019"
            )
        )
    }

    private val movie by lazy {
        Movie(
            title = "Movie title",
            year = "2019"
        )
    }

    @Before
    fun `before each test`() {
        usecase = FetchMovies(omdbService)

        whenever(omdbService.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(movies)
        whenever(omdbService.fetchMovie(anyString()))
            .thenReturn(movie)
    }

    @Test
    fun `should search valid title`() {
        assertThat(usecase.search("Avengers", "", "")).isEqualTo(movies)
    }

    @Test
    fun `should search invalide title`() {
        whenever(omdbService.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(emptyList())
        try {
            usecase.search("Avengers")
        } catch (error: Throwable) {
            assertThat(error).isEqualTo(SearchMoviesError.NoResultsFound)
        }
    }

    @Test
    fun `should throw with invalid term`() {
        try {
            usecase.search("")
        } catch (error: Throwable) {
            assertThat(error).isEqualTo(SearchMoviesError.EmptyTerm)
        }
    }

    @Test
    fun `should fetch valid id`() {
        assertThat(usecase.fetch("10")).isEqualTo(movie)
    }

    @Test
    fun `should throw with invalid id`() {
        try {
            usecase.fetch("")
        } catch (error: Throwable) {
            assertThat(error).isEqualTo(SearchMoviesError.EmptyTerm)
        }
    }
}