package com.meteoro.omdbarch.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.navigator.MyNavigator
import com.meteoro.omdbarch.utilities.Disposer
import com.meteoro.omdbarch.utilities.ErrorStateResources
import com.meteoro.omdbarch.utilities.ViewState
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
        lifecycle.addObserver(disposer)

        loadMovie(intent.getStringExtra(MyNavigator.ARG_MOVIE))
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
            is ViewState.Done -> finishExecution()
        }
    }

    private fun showMovie(movie: MovieDetailPresentation) {
        logger.i("Loaded Movies")

        groupDetailView.visibility = View.VISIBLE
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
        groupStateView.visibility = View.VISIBLE
        errorStateImage.setImageResource(errorImage)
        errorStateLabel.setText(errorMessage)
    }

    private fun emptyTerm() {
        groupDetailView.visibility = View.GONE
        groupStateView.visibility = View.GONE
    }

    private fun startExecution() {
        loadingView.visibility = View.VISIBLE
        groupDetailView.visibility = View.GONE
        groupStateView.visibility = View.GONE
    }

    private fun finishExecution() {
        loadingView.visibility = View.GONE
    }
}