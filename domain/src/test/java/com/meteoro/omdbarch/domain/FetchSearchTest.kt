package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.domain.util.noResultSearch
import com.meteoro.omdbarch.domain.util.resultSearch
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class FetchSearchTest {

    private val service = mock<SearchService>()
    private lateinit var usecase: FetchSearch

    @Before
    fun `before each test`() {
        usecase = FetchSearch(service)

        whenever(service.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Flowable.just(resultSearch))
    }

    @Test
    fun `should search valid title`() {
        simpleFetchSearch().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(resultSearch)

        verify(service, times(timeInvocation()))
            .searchMovies("Avengers", "", "")
    }

    @Test
    fun `should search invalid title`() {
        whenever(service.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Flowable.just(noResultSearch))

        simpleFetchSearch().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)

        verify(service, times(timeInvocation()))
            .searchMovies(anyString(), anyString(), anyString())
    }

    @Test
    fun `should error when search empty title`() {
        usecase.searchMovies("")

        verify(service, never())
            .searchMovies(anyString(), anyString(), anyString())
    }

    private fun timeInvocation() = 1

    private fun simpleFetchSearch() =
        usecase.searchMovies("Avengers", "", "")
}