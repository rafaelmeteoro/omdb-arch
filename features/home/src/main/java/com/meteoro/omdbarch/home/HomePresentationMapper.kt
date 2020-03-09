package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.model.TypeMovie

class HomePresentationMapper {

    companion object {
        private const val defaultTitle = "Sem título"
        private const val defaultPoster =
            "https://www.bauducco.com.br/wp/wordpress/wp-content/uploads/2017/09/default-placeholder-1-2.png"
        private const val defaultResponse = "False"
        private const val defaultImdbId = "NoId"

        private fun getType(type: String?): TypeMovie {
            if (type == null) return TypeMovie.OUTRO
            return TypeMovie.fromString(type) ?: TypeMovie.OUTRO
        }
    }

    fun fromDomain(info: ResultSearch): HomePresentation =
        HomePresentation(
            movies = info.search?.map {
                MoviePresentation(
                    imdbId = it.imdbId ?: defaultImdbId,
                    title = it.title ?: defaultTitle,
                    photoUrl = it.poster ?: defaultPoster,
                    type = getType(it.type)
                )
            } ?: emptyList(),
            response = info.response ?: defaultResponse,
            totalResults = info.totalResults
        )
}