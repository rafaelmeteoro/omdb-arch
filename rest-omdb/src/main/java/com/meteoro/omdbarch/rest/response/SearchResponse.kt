package com.meteoro.omdbarch.rest.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Search") val search: List<MovieResponse>? = null
)