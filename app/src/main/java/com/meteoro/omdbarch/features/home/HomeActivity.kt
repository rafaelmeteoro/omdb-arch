package com.meteoro.omdbarch.features.home

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.R
import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.features.details.MovieDetailActivity
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.*
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity(), KodeinAware {

    companion object {
        private const val COLUMN_COUNT = 3
        private const val DEBOUNCE_TIME = 750L
    }

    override val kodein: Kodein by closestKodein()

    private val viewModel by instance<HomeViewModel>()
    private val disposer by instance<Disposer>()
    private val logger by instance<Logger>()

    private val searchSubject = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupView()
        setupSubject()

        lifecycle.addObserver(disposer)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        (menu?.findItem(R.id.action_search)?.actionView as? SearchView)?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchSubject.onNext(newText ?: "")
                return true
            }
        })
        return true
    }

    private fun setupView() {
        homeView.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, COLUMN_COUNT)
            addItemDecoration(
                GridItemDecoration(
                    gridSpacingPx = resources.getDimensionPixelSize(R.dimen.movies_list_spacing),
                    gridSize = COLUMN_COUNT
                )
            )
        }

        swipeToRefresh.apply {
            setColorSchemeColors(ContextCompat.getColor(this@HomeActivity, R.color.colorPrimary))
            setOnRefreshListener {
                finishExecution()
            }
        }

        setSupportActionBar(toolbar)
    }

    private fun setupSubject() {
        val toDispose = searchSubject
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeBy(
                onNext = { searchMovie(it) }
            )

        disposer.collect(toDispose)
    }

    private fun searchMovie(title: String) {
        val toDispose = viewModel
            .searchMovie(title)
            .subscribeBy(
                onNext = { changeState(it) },
                onError = { logger.e("Error -> $it") }
            )

        disposer.collect(toDispose)
    }

    private fun changeState(event: UIEvent<HomePresentation>) {
        when (event) {
            is Launched -> startExecution()
            is Result -> showMovies(event.value)
            is Failed -> handleError(event.reason)
            is Done -> finishExecution()
        }
    }

    private fun showMovies(home: HomePresentation) {
        logger.i("Loaded Movies")

        homeView.visibility = View.VISIBLE
        homeView.adapter = MoviesAdapter(home) {
            startActivity(MovieDetailActivity.newIntent(this, it.imdbId))
        }
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
        val hasPreviousContent =
            homeView.adapter
                ?.let { it.itemCount != 0 }
                ?: false

        if (hasPreviousContent) {
            errorStateLabel.apply {
                text = message
                visibility = View.VISIBLE
            }
        }

        Snackbar.make(homeRoot, message, Snackbar.LENGTH_LONG).show()
    }

    private fun startExecution() {
        homeView.visibility = View.INVISIBLE
        swipeToRefresh.isRefreshing = true
        errorStateLabel.visibility = View.GONE
    }

    private fun finishExecution() {
        swipeToRefresh.isRefreshing = false
    }
}