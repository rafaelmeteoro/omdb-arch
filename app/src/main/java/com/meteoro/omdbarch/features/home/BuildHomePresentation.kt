package com.meteoro.omdbarch.features.home

import com.meteoro.omdbarch.domain.model.ResultSearch

object BuildHomePresentation {

    operator fun invoke(info: ResultSearch) =
        HomePresentation(
            movies = info.search?.map {
                MoviePresentation(
                    imdbId = it.imdbId ?: defaultImdbId,
                    title = it.title ?: defaultTitle,
                    photoUrl = it.poster ?: defaultPoster
                )
            } ?: emptyList(),
            response = info.response ?: defaultResponse,
            totalResults = info.totalResults
        )

    private const val defaultTitle = "Sem título"
    private const val defaultPoster =
        "https://www.bauducco.com.br/wp/wordpress/wp-content/uploads/2017/09/default-placeholder-1-2.png"
    private const val defaultResponse = "False"
    private const val defaultImdbId = "NoId"
}