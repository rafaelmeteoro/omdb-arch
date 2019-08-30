package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import com.meteoro.omdbarch.domain.util.historyResult
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

class ManagerSearchTests {

    private val service = mock<SearchHistoryService>()
    private lateinit var manager: ManagerSearch

    @Before
    fun `before each test`() {
        manager = ManagerSearch(service)
    }

    @Test
    fun `should return error when no has history`() {
        whenever(service.lastSearches())
            .thenReturn(emptyList())

        manager.fetchSearchList().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)
    }

    @Test
    fun `should return a list when has history`() {
        whenever(service.lastSearches())
            .thenReturn(historyResult)

        manager.fetchSearchList().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(historyResult)
    }
}