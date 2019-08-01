package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class FetchMoviesTests {

    private val omdbService = mock<RemoteOmdbService>()
    private lateinit var usecase: FetchMovies

    private val movie by lazy {
        Movie(
            title = "Movie title",
            year = "2019"
        )
    }

    private val resultSearch by lazy {
        ResultSearch(
            search = listOf(movie),
            totalResults = 1,
            response = "True"
        )
    }

    private val noResultSearch by lazy {
        ResultSearch(
            response = "False"
        )
    }

    @Before
    fun `before each test`() {
        usecase = FetchMovies(omdbService)

        whenever(omdbService.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Observable.just(resultSearch))
        whenever(omdbService.fetchMovie(anyString()))
            .thenReturn(Observable.just(movie))
    }

    @Test
    fun `should search valid title`() {
        simpleSearchMovies().test()
            .assertComplete()
            .assertTerminated()
            .assertNoErrors()
            .assertValue(resultSearch)
    }

    @Test
    fun `should search invalide title`() {
        whenever(omdbService.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Observable.just(noResultSearch))

        simpleSearchMovies().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)
    }

    @Test
    fun `should throw with invalid term`() {
        simpleFetchMovie("").test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(EmptyTerm)
    }

    @Test
    fun `should fetch valid id`() {
        simpleFetchMovie("10").test()
            .assertComplete()
            .assertTerminated()
            .assertNoErrors()
            .assertValue(movie)
    }

    private fun simpleSearchMovies() =
        usecase.searchMovies("Avenges", "", "")

    private fun simpleFetchMovie(id: String) =
        usecase.fetchMovie(id)
}