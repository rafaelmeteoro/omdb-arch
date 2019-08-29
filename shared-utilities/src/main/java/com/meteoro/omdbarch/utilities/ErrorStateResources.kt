package com.meteoro.omdbarch.utilities

import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError.RemoteSystem
import com.meteoro.omdbarch.domain.errors.SearchMoviesError

data class ErrorStateResources(
    val image: Int,
    val message: Int
) {
    companion object {
        operator fun invoke(error: Throwable) =
            when (error) {
                is RemoteSystem -> ErrorStateResources(
                    R.drawable.img_server_down,
                    R.string.error_server_down
                )
                is NetworkingError -> ErrorStateResources(
                    R.drawable.img_network_issue,
                    R.string.error_network
                )
                is SearchMoviesError.NoResultsFound -> ErrorStateResources(
                    R.drawable.img_no_results,
                    R.string.error_no_results
                )
                else -> ErrorStateResources(
                    R.drawable.img_bug_found,
                    R.string.error_bug_found
                )
            }
    }
}