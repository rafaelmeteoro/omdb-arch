package com.meteoro.omdbarch.favorites.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.favorites.R
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.Disposer
import com.meteoro.omdbarch.utilities.ViewState
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_movie_details.*
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
        lifecycle.addObserver(disposer)
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
            is ViewState.Launched -> startExecution()
            is ViewState.Success -> handlePresentation(event.value)
            is ViewState.Failed -> handleError(event.reason)
            is ViewState.Done -> finishExecution()
        }
    }

    private fun handlePresentation(presentation: MovieDetailsPresentation) {
        logger.d("${presentation.movie}")

        val movie = presentation.movie
        groupDetailsView.visibility = View.VISIBLE

        Picasso.get().load(movie.poster).into(movieDetailPoster)
        movieDetailTitle.text = movie.title
        movieDetailYear.text = movie.year
        movieDetailPlot.text = movie.plot
        movieDetailActors.text = movie.actors
        movieDetailDirector.text = movie.director
    }

    private fun handleError(reason: Throwable) {
        logger.e("Failed to load movies -> $reason")

        if (reason is NoResultsFound) {
            groupDetailsView.visibility = View.GONE
            groupStateView.visibility = View.VISIBLE
            return
        }

        showErrorReport(R.string.fragment_movie_detail_error)
    }

    private fun startExecution() {
        loadingMovie.visibility = View.VISIBLE
        groupDetailsView.visibility = View.GONE
        groupStateView.visibility = View.GONE
    }

    private fun finishExecution() {
        loadingMovie.visibility = View.GONE
    }

    private fun showErrorReport(targetMessageId: Int) {
        Snackbar
            .make(movieDetailsScreenRoot, targetMessageId, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}