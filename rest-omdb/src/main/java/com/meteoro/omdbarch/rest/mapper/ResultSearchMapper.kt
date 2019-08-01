package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.rest.response.SearchResponse

object ResultSearchMapper {

    operator fun invoke(responseApi: SearchResponse) = with(responseApi) {
        ResultSearch(
            search = search?.map { MovieMapper(it) },
            totalResults = totalResult,
            response = response
        )
    }
}