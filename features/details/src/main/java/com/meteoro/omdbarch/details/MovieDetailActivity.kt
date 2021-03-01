package com.meteoro.omdbarch.details

import android.os.Bundle
import android.view.LayoutInflater
import coil.load
import com.meteoro.omdbarch.actions.EXTRA_IMDB
import com.meteoro.omdbarch.actions.ImdbArgs
import com.meteoro.omdbarch.components.ErrorStateResources
import com.meteoro.omdbarch.components.binding.BindingActivity
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState
import com.meteoro.omdbarch.details.databinding.ActivityMovieDetailBinding
import com.meteoro.omdbarch.details.databinding.StateDetailContentBinding
import com.meteoro.omdbarch.details.databinding.StateDetailErrorBinding
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.state.ViewState
import dagger.android.AndroidInjection
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MovieDetailActivity : BindingActivity<ActivityMovieDetailBinding>() {

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: DetailViewModelContract

    private lateinit var bindingContent: StateDetailContentBinding
    private lateinit var bindingError: StateDetailErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityMovieDetailBinding {
        val movieDetailBinding = ActivityMovieDetailBinding.inflate(inflater)
        bindingContent = StateDetailContentBinding.bind(movieDetailBinding.stateView.getView(FacedViewState.CONTENT))
        bindingError = StateDetailErrorBinding.bind(movieDetailBinding.stateView.getView(FacedViewState.ERROR))
        return movieDetailBinding
    }

    override fun init() {
        val args = intent.getParcelableExtra(EXTRA_IMDB) as ImdbArgs
        loadMovie(args.imdbId)
    }

    private fun loadMovie(imdbId: String) {
        val toDispose = viewModel
            .fetchMovie(imdbId)
            .subscribeBy(
                onNext = { changeState(it) },
                onError = { Timber.e("Error -> $it") }
            )

        disposer.collect(toDispose)
    }

    private fun changeState(event: ViewState<MovieDetailPresentation>) {
        when (event) {
            is ViewState.Launched -> startExecution()
            is ViewState.Success -> showMovie(event.value)
            is ViewState.Failed -> handleError(event.reason)
            is ViewState.Done -> Unit
        }
    }

    private fun showMovie(movie: MovieDetailPresentation) {
        Timber.i("Loaded Movies")

        binding.stateView.setState(FacedViewState.CONTENT)
        bindingContent.detailTitle.text = movie.title
        bindingContent.detailYear.text = movie.year
        bindingContent.detailRating.text = movie.rating
        bindingContent.detailCast.text = movie.cast
        bindingContent.detailDirectors.text = movie.directors
        bindingContent.detailPlot.text = movie.plot
        bindingContent.detailPoster.load(movie.poster) {
            crossfade(true)
        }
    }

    private fun handleError(reason: Throwable) {
        Timber.e("Error -> $reason")

        if (reason is EmptyTerm) {
            emptyTerm()
            return
        }

        val (errorImage, errorMessage) = ErrorStateResources(reason)
        reportError(errorImage, errorMessage)
    }

    private fun reportError(errorImage: Int, errorMessage: Int) {
        binding.stateView.setState(FacedViewState.ERROR)
        bindingError.errorStateImage.setImageResource(errorImage)
        bindingError.errorStateLabel.setText(errorMessage)
    }

    private fun emptyTerm() {
        binding.stateView.setState(FacedViewState.EMPTY)
    }

    private fun startExecution() {
        binding.stateView.setState(FacedViewState.LOADING)
    }
}
