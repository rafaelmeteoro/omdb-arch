package com.meteoro.omdbarch.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.navigator.MyNavigator
import com.meteoro.omdbarch.utilities.Disposer
import com.meteoro.omdbarch.utilities.ErrorStateResources
import com.meteoro.omdbarch.utilities.ViewState
import com.meteoro.omdbarch.utilities.ViewState.*
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupView()
        loadMovie(intent.getStringExtra(MyNavigator.ARG_MOVIE))

        lifecycle.addObserver(disposer)
    }

    private fun setupView() {
        swipeToRefresh.apply {
            setColorSchemeColors(ContextCompat.getColor(this@MovieDetailActivity, R.color.colorPrimary))
            setOnRefreshListener {
                finishExecution()
            }
        }
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
            is Launched -> startExecution()
            is Success -> showMovie(event.value)
            is Failed -> handleError(event.reason)
            is Done -> finishExecution()
        }
    }

    private fun showMovie(movie: MovieDetailPresentation) {
        logger.i("Loaded Movies")

        detailView.visibility = View.VISIBLE
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
        with(errorStateView) {
            visibility = View.VISIBLE
            errorStateImage.setImageResource(errorImage)
            errorStateLabel.setText(errorMessage)
        }
    }

    private fun emptyTerm() {
        detailView.visibility = View.GONE
        errorStateView.visibility = View.GONE
    }

    private fun startExecution() {
        detailView.visibility = View.GONE
        swipeToRefresh.isRefreshing = true
        errorStateLabel.visibility = View.GONE
    }

    private fun finishExecution() {
        swipeToRefresh.isRefreshing = false
    }
}