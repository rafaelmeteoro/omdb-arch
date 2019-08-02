package com.meteoro.omdbarch.rest.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("Search") val search: List<MovieResponse>? = null,
    @SerialName("totalResults") val totalResult: Int = 0,
    @SerialName("Response") val response: String
)