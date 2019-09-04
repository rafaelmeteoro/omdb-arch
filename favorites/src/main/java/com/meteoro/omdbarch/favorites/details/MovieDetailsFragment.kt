package com.meteoro.omdbarch.favorites.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meteoro.omdbarch.favorites.R
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.Disposer
import com.meteoro.omdbarch.utilities.ViewState
import com.meteoro.omdbarch.utilities.ViewState.*
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: MovieDetailsViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imdbId = arguments?.let { MovieDetailsFragmentArgs.fromBundle(it).imdbIdArg } ?: ""
        getMovieSaved(imdbId)
    }

    private fun getMovieSaved(imdbId: String) {
        val toDispose = viewModel
            .fetchMovieSaved(imdbId)
            .subscribeBy(
                onNext = { changeState(it) },
                onError = { logger.e("Error -> $it") }
            )

        disposer.collect(toDispose)
    }

    private fun changeState(event: ViewState<MovieDetailsPresentation>) {
        when (event) {
            is Launched -> startExecution()
            is Success -> handlePresentation(event.value)
            is Failed -> handleError(event.reason)
            is Done -> finishExecution()
        }
    }

    private fun handlePresentation(presentation: MovieDetailsPresentation) {
        logger.d("${presentation.movie}")
    }

    private fun handleError(reason: Throwable) {
        logger.e("Failed to load movies -> $reason")
    }

    private fun startExecution() {}

    private fun finishExecution() {}
}