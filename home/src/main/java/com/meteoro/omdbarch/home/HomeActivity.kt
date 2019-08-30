package com.meteoro.omdbarch.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.navigator.MyNavigator
import com.meteoro.omdbarch.utilities.Disposer
import com.meteoro.omdbarch.utilities.ErrorStateResources
import com.meteoro.omdbarch.utilities.GridItemDecoration
import com.meteoro.omdbarch.utilities.ViewState
import com.meteoro.omdbarch.utilities.ViewState.*
import dagger.android.AndroidInjection
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val COLUMN_COUNT = 3
        private const val DEBOUNCE_TIME = 750L
    }

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var navigator: MyNavigator

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: HomeViewModel

    private val searchSubject = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupView()
        setupSubject()

        lifecycle.addObserver(disposer)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        (menu?.findItem(R.id.action_search)?.actionView as? SearchView)?.apply {
            queryHint = getString(R.string.action_search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true
                override fun onQueryTextChange(newText: String?): Boolean {
                    searchSubject.onNext(newText ?: "")
                    return true
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_favorites -> {
            navigator.navigateToFavorites(this)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        homeView.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, COLUMN_COUNT)
            addItemDecoration(
                GridItemDecoration(
                    space = resources.getDimensionPixelSize(R.dimen.movies_list_spacing),
                    noOfColumns = COLUMN_COUNT
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

    private fun changeState(event: ViewState<HomePresentation>) {
        when (event) {
            is Launched -> startExecution()
            is Success -> showMovies(event.value)
            is Failed -> handleError(event.reason)
            is Done -> finishExecution()
        }
    }

    private fun showMovies(home: HomePresentation) {
        logger.i("Loaded Movies")

        homeView.visibility = View.VISIBLE
        homeView.adapter = MoviesAdapter(home) {
            navigator.navigateToDetail(this, it.imdbId)
        }
    }

    private fun handleError(reason: Throwable) {
        logger.e("Error -> $reason")

        if (reason is EmptyTerm) {
            emptyTerm()
            return
        }

        val (errorImage, errorMessage) = ErrorStateResources(reason)
        val hasPreviousContent =
            homeView.adapter
                ?.let { it.itemCount != 0 }
                ?: false

        when {
            hasPreviousContent -> Snackbar.make(homeRoot, errorMessage, Snackbar.LENGTH_LONG).show()
            else -> reportError(errorImage, errorMessage)
        }
    }

    private fun reportError(errorImage: Int, errorMessage: Int) {
        with(errorStateView) {
            visibility = View.VISIBLE
            errorStateImage.setImageResource(errorImage)
            errorStateLabel.setText(errorMessage)
        }
    }

    private fun emptyTerm() {
        homeView.visibility = View.INVISIBLE
        errorStateView.visibility = View.GONE
    }

    private fun startExecution() {
        homeView.visibility = View.INVISIBLE
        swipeToRefresh.isRefreshing = true
        errorStateView.visibility = View.GONE
    }

    private fun finishExecution() {
        swipeToRefresh.isRefreshing = false
    }
}