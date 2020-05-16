package com.meteoro.omdbarch.favorites.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.state.ViewState
import com.meteoro.omdbarch.favorites.R
import com.meteoro.omdbarch.favorites.databinding.FragmentMovieDetailsBinding
import com.meteoro.omdbarch.favorites.databinding.StateDetailsContentBinding
import com.meteoro.omdbarch.logger.Logger
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

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var bindingContent: StateDetailsContentBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        bindingContent = StateDetailsContentBinding.bind(getContentView())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(disposer)
        val imdbId = arguments?.let { MovieDetailsFragmentArgs.fromBundle(it).imdbIdArg } ?: ""
        getMovieSaved(imdbId)
    }

    private fun getContentView() = binding.stateView.getView<View>(FacedViewState.CONTENT)

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
        binding.stateView.setState(FacedViewState.CONTENT)

        bindingContent.movieDetailPoster.load(movie.poster) {
            crossfade(true)
        }
        bindingContent.movieDetailTitle.text = movie.title
        bindingContent.movieDetailYear.text = movie.year
        bindingContent.movieDetailPlot.text = movie.plot
        bindingContent.movieDetailActors.text = movie.actors
        bindingContent.movieDetailDirector.text = movie.director
    }

    private fun handleError(reason: Throwable) {
        logger.e("Failed to load movies -> $reason")

        if (reason is NoResultsFound) {
            binding.stateView.setState(FacedViewState.EMPTY)
            return
        }

        binding.stateView.setState(FacedViewState.ERROR)
        showErrorReport(R.string.fragment_movie_detail_error)
    }

    private fun startExecution() {
        binding.stateView.setState(FacedViewState.LOADING)
    }

    private fun showErrorReport(targetMessageId: Int) {
        Snackbar
            .make(binding.movieDetailsScreenRoot, targetMessageId, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}