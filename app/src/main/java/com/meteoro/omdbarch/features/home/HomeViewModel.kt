package com.meteoro.omdbarch.features.home

import androidx.lifecycle.ViewModel
import com.meteoro.omdbarch.domain.FetchSearch
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val fetch: FetchSearch,
    private val scheduler: Scheduler
) : ViewModel() {

    companion object {
        private const val DEBOUNCE_TIME = 750L
    }

    fun searchMovie(movieTitle: String) =
        fetch.searchMovies(movieTitle)
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .observeOn(scheduler)
}