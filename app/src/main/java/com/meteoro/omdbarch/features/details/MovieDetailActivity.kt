package com.meteoro.omdbarch.features.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.meteoro.omdbarch.R
import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.*
import com.squareup.picasso.Picasso
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MovieDetailActivity : AppCompatActivity(), KodeinAware {

    companion object {
        const val MOVIE_ARG = "MOVIE_ARG"

        fun newIntent(context: Context, imdbId: String) =
            Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ARG, imdbId)
            }
    }

    override val kodein: Kodein by closestKodein()

    private val viewModel by instance<DetailViewModel>()
    private val disposer by instance<Disposer>()
    private val logger by instance<Logger>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupView()
        loadMovie(intent.getStringExtra(MOVIE_ARG))

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

    private fun changeState(event: UIEvent<MovieDetailPresentation>) {
        when (event) {
            is Launched -> startExecution()
            is Result -> showMovie(event.value)
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

        when (reason) {
            is NetworkingError,
            is RemoteServiceIntegrationError -> {
                reportError(reason.toString())
            }
        }
    }

    private fun reportError(message: String) {
        errorStateLabel.apply {
            text = message
            visibility = View.VISIBLE
        }
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