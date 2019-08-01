package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.util.movie
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class FetchMovieTests {

    private val service = mock<MovieService>()
    private lateinit var usecase: FetchMovie

    @Before
    fun `before each test`() {
        usecase = FetchMovie(service)

        whenever(service.fetchMovie(anyString()))
            .thenReturn(Observable.just(movie))
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

    private fun simpleFetchMovie(id: String) =
        usecase.fetchMovie(id)
}