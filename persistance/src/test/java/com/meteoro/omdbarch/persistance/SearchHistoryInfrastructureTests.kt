package com.meteoro.omdbarch.persistance

import com.meteoro.omdbarch.domain.errors.SearchHistoryError
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class SearchHistoryInfrastructureTests {

    lateinit var service: SearchHistoryService

    @Before
    fun `before each test`() {
        service = SearchHistoryInfrastructure(FakePreferences)

        FakePreferences.run {
            brokenMode = false
            storage.clear()
        }
    }

    @Test
    fun `should retrive empty search history`() {
        val noHistory = emptyList<String>()
        assertThat(service.lastSearches()).isEqualTo(noHistory)
    }

    @Test
    fun `should store new search value`() {
        service.registerNewSearch("Marvel")
        val newValue = listOf("Marvel")
        assertThat(service.lastSearches()).isEqualTo(newValue)
    }

    @Test
    fun `should not persist the same value more than once`() {
        repeat(times = 3) { service.registerNewSearch("Marvel") }
        val newValue = listOf("Marvel")
        assertThat(service.lastSearches()).isEqualTo(newValue)
    }

    @Test
    fun `should report broken preferences values`() {
        FakePreferences.brokenMode = true

        try {
            service.lastSearches()
        } catch (error: Throwable) {
            assertThat(error).isEqualTo(SearchHistoryError)
        }
    }
}