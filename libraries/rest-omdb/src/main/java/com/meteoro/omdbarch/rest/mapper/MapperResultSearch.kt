package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.rest.response.SearchResponse

class MapperResultSearch {

    fun fromResponse(response: SearchResponse): ResultSearch =
        ResultSearch(
            search = response.search?.map { MapperMovie().fromResponse(it) },
            totalResults = response.totalResult,
            response = response.response
        )
}
