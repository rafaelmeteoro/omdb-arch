package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import com.meteoro.omdbarch.domain.util.historyResult
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class ManagerSearchTest {

    private val service = mock<SearchHistoryService>()
    private lateinit var manager: ManagerRepository

    @Before
    fun `before each test`() {
        manager = ManagerSearch(service)
    }

    @Test
    fun `should return error when no has history`() {
        val timeInvocation = 1

        whenever(service.lastSearches())
            .thenReturn(emptyList())

        manager.fetchSearchList().test()
            .assertNotComplete()
            .assertTerminated()
            .assertError(NoResultsFound)

        verify(service, times(timeInvocation)).lastSearches()
        verify(service, never()).registerNewSearch(anyString())
        verify(service, never()).unregisterSearch(anyString())
    }

    @Test
    fun `should return a list when has history`() {
        val timeInvocation = 1

        whenever(service.lastSearches())
            .thenReturn(historyResult)

        manager.fetchSearchList().test()
            .assertComplete()
            .assertTerminated()
            .assertValue(historyResult)

        verify(service, times(timeInvocation)).lastSearches()
        verify(service, never()).registerNewSearch(anyString())
        verify(service, never()).unregisterSearch(anyString())
    }

    @Test
    fun `should register search when provided`() {
        val search = "search"
        val timeInvocation = 1

        manager.save(search)

        verify(service, times(timeInvocation)).registerNewSearch(search)
        verify(service, never()).unregisterSearch(anyString())
        verify(service, never()).lastSearches()
    }

    @Test
    fun `should unregister search when provided`() {
        val search = "search"
        val timeInvocation = 1

        manager.delete(search)

        verify(service, times(timeInvocation)).unregisterSearch(search)
        verify(service, never()).registerNewSearch(anyString())
        verify(service, never()).lastSearches()
    }
}