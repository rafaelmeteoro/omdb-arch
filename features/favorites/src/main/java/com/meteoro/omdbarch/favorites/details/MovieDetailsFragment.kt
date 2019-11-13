package com.meteoro.omdbarch.favorites.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.components.Disposer
import com.meteoro.omdbarch.components.ViewState
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.ManyFacedView
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.favorites.R
import com.meteoro.omdbarch.logger.Logger
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

    private lateinit var stateView: ManyFacedView
    private lateinit var movieDetailPoster: ImageView
    private lateinit var movieDetailTitle: TextView
    private lateinit var movieDetailYear: TextView
    private lateinit var movieDetailPlot: TextView
    private lateinit var movieDetailActors: TextView
    private lateinit var movieDetailDirector: TextView

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        stateView = view.findViewById(R.id.state_view)
        movieDetailPoster = view.findViewById(R.id.movie_detail_poster)
        movieDetailTitle = view.findViewById(R.id.movie_detail_title)
        movieDetailYear = view.findViewById(R.id.movie_detail_year)
        movieDetailPlot = view.findViewById(R.id.movie_detail_plot)
        movieDetailActors = view.findViewById(R.id.movie_detail_actors)
        movieDetailDirector = view.findViewById(R.id.movie_detail_director)

        return view
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
            is ViewState.Done -> Unit
        }
    }

    private fun handlePresentation(presentation: MovieDetailsPresentation) {
        logger.d("${presentation.movie}")

        val movie = presentation.movie
        stateView.setState(FacedViewState.CONTENT)

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
            stateView.setState(FacedViewState.EMPTY)
            return
        }

        stateView.setState(FacedViewState.ERROR)
        showErrorReport(R.string.fragment_movie_detail_error)
    }

    private fun startExecution() {
        stateView.setState(FacedViewState.LOADING)
    }

    private fun showErrorReport(targetMessageId: Int) {
        Snackbar
            .make(movieDetailsScreenRoot, targetMessageId, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}