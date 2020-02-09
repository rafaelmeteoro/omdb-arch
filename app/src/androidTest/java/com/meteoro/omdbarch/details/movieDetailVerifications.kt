package com.meteoro.omdbarch.details

import com.meteoro.omdbarch.dsl.ViewVisibilityChecker

fun movieDetailListChecks(block: MovieDetailActivityChecker.() -> Unit) = MovieDetailActivityChecker().apply { block() }

class MovieDetailActivityChecker {
    val loadingIndicator = ViewVisibilityChecker(R.id.loading_view)
    val title = ViewVisibilityChecker(R.id.detail_title)
}