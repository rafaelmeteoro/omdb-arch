package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.domain.util.noResultSearch
import com.meteoro.omdbarch.domain.util.resultSearch
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class FetchSearchTests {

    private val service = mock<SearchService>()
    private lateinit var usecase: FetchSearch

    @Before
    fun `before each test`() {
        usecase = FetchSearch(service)

        whenever(service.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Observable.just(resultSearch))
        whenever(service.searchMovies(anyString()))
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
    fun `should search valid title flowable`() {
        simpleFetchSearchFlowable().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(resultSearch)

        verify(service, times(timeInvocation()))
            .searchMovies("Avengers")
    }

    @Test
    fun `should search invalid title`() {
        whenever(service.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Observable.just(noResultSearch))

        simpleFetchSearch().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)

        verify(service, times(timeInvocation()))
            .searchMovies(anyString(), anyString(), anyString())
    }

    @Test
    fun `should search invalid title flowable`() {
        whenever(service.searchMovies(anyString()))
            .thenReturn(Flowable.just(noResultSearch))

        simpleFetchSearchFlowable().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)

        verify(service, times(timeInvocation()))
            .searchMovies(anyString())
    }

    @Test
    fun `should error when search empty title`() {
        usecase.searchMovies("", "", "")

        verify(service, never())
            .searchMovies(anyString(), anyString(), anyString())
    }

    @Test
    fun `should error when search empty title flowable`() {
        usecase.searchMovies("")

        verify(service, never())
            .searchMovies(anyString())
    }

    private fun timeInvocation() = 1

    private fun simpleFetchSearch() =
        usecase.searchMovies("Avengers", "", "")

    private fun simpleFetchSearchFlowable() =
        usecase.searchMovies("Avengers")
}