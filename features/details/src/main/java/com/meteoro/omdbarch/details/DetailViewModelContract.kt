package com.meteoro.omdbarch.details

import com.meteoro.omdbarch.domain.state.ViewState
import io.reactivex.Flowable

interface DetailViewModelContract {
    fun fetchMovie(idImdb: String): Flowable<ViewState<MovieDetailPresentation>>
}
