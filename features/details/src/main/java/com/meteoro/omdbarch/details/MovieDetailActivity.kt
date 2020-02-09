package com.meteoro.omdbarch.details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.actions.EXTRA_IMDB
import com.meteoro.omdbarch.actions.ImdbArgs
import com.meteoro.omdbarch.components.Disposer
import com.meteoro.omdbarch.components.ErrorStateResources
import com.meteoro.omdbarch.components.ViewState
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.ManyFacedView
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.logger.Logger
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: DetailViewModelContract

    private lateinit var stateView: ManyFacedView
    private lateinit var detailTitle: TextView
    private lateinit var detailYear: TextView
    private lateinit var detailRating: TextView
    private lateinit var detailCast: TextView
    private lateinit var detailDirectors: TextView
    private lateinit var detailPlot: TextView
    private lateinit var detailPoster: ImageView
    private lateinit var errorStateImage: ImageView
    private lateinit var errorStateLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        lifecycle.addObserver(disposer)

        initViews()
        val args = intent.getParcelableExtra(EXTRA_IMDB) as ImdbArgs
        loadMovie(args.imdbId)
    }

    private fun loadMovie(imdbId: String) {
        val toDispose = viewModel
            .fetchMovie(imdbId)
            .subscribeBy(
                onNext = { changeState(it) },
                onError = { logger.e("Error -> $it") }
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

    private fun initViews() {
        stateView = findViewById(R.id.state_view)
        detailTitle = findViewById(R.id.detail_title)
        detailYear = findViewById(R.id.detail_year)
        detailRating = findViewById(R.id.detail_rating)
        detailCast = findViewById(R.id.detail_cast)
        detailDirectors = findViewById(R.id.detail_directors)
        detailPlot = findViewById(R.id.detail_plot)
        detailPoster = findViewById(R.id.detail_poster)
        errorStateImage = findViewById(R.id.error_state_image)
        errorStateLabel = findViewById(R.id.error_state_label)
    }

    private fun showMovie(movie: MovieDetailPresentation) {
        logger.i("Loaded Movies")

        stateView.setState(FacedViewState.CONTENT)
        detailTitle.text = movie.title
        detailYear.text = movie.year
        detailRating.text = movie.rating
        detailCast.text = movie.cast
        detailDirectors.text = movie.directors
        detailPlot.text = movie.plot
        Picasso.get().load(movie.poster).into(detailPoster)
    }

    private fun handleError(reason: Throwable) {
        logger.e("Error -> $reason")

        if (reason is EmptyTerm) {
            emptyTerm()
            return
        }

        val (errorImage, errorMessage) = ErrorStateResources(reason)
        reportError(errorImage, errorMessage)
    }

    private fun reportError(errorImage: Int, errorMessage: Int) {
        stateView.setState(FacedViewState.ERROR)
        errorStateImage.setImageResource(errorImage)
        errorStateLabel.setText(errorMessage)
    }

    private fun emptyTerm() {
        stateView.setState(FacedViewState.EMPTY)
    }

    private fun startExecution() {
        stateView.setState(FacedViewState.LOADING)
    }
}