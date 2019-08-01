package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.domain.util.noResultSearch
import com.meteoro.omdbarch.domain.util.resultSearch
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
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
    }

    @Test
    fun `should search valid title`() {
        simpleFetchSearch().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(resultSearch)
    }

    @Test
    fun `should search invalid title`() {
        whenever(service.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Observable.just(noResultSearch))

        simpleFetchSearch().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)
    }

    private fun simpleFetchSearch() =
        usecase.searchMovies("Avengers", "", "")
}