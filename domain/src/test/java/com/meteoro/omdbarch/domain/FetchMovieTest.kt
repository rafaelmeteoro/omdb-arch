package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.util.movie
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class FetchMovieTest {

    private val service = mock<MovieService>()
    private lateinit var usecase: FetchMovie

    @Before
    fun `before each test`() {
        usecase = FetchMovie(service)

        whenever(service.fetchMovie(anyString()))
            .thenReturn(Flowable.just(movie))
    }

    @Test
    fun `should throw with invalid term`() {
        val id = ""

        simpleFetchMovie(id).test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(EmptyTerm)

        verify(service, never()).fetchMovie(id)
    }

    @Test
    fun `should fetch valid id`() {
        val id = "10"
        val timeInvocation = 1

        simpleFetchMovie(id).test()
            .assertComplete()
            .assertTerminated()
            .assertNoErrors()
            .assertValue(movie)

        verify(service, times(timeInvocation)).fetchMovie(id)
    }

    private fun simpleFetchMovie(id: String) =
        usecase.fetchMovie(id)
}