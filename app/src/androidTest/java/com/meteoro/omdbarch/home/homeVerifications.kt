package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.dsl.CheckStringVisibility
import com.meteoro.omdbarch.dsl.ViewVisibilityChecker

fun homeListChecks(block: HomeActivityChecker.() -> Unit) = HomeActivityChecker().apply { block() }

class HomeActivityChecker {
    val loadingIndicator = ViewVisibilityChecker(R.id.loading_view)
    val errorLabel = CheckStringVisibility(R.string.empty_results)
    val emptyState = ViewVisibilityChecker(R.id.error_state_label)
}
