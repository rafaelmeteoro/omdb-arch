package com.meteoro.omdbarch.domain.services

interface SearchHistoryService {
    fun lastSearches(): List<String>
    fun registerNewSearch(search: String)
}