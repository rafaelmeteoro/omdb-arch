package com.meteoro.omdbarch.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.components.Disposer
import com.meteoro.omdbarch.components.ErrorStateResources
import com.meteoro.omdbarch.components.GridItemDecoration
import com.meteoro.omdbarch.components.ViewState
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.navigator.MyNavigator
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
        lifecycle.addObserver(disposer)

        setupView()
        setupSubject()
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
            is ViewState.Launched -> startExecution()
            is ViewState.Success -> showMovies(event.value)
            is ViewState.Failed -> handleError(event.reason)
            is ViewState.Done -> finishExecution()
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
        groupStateView.visibility = View.VISIBLE
        errorStateImage.setImageResource(errorImage)
        errorStateLabel.setText(errorMessage)
    }

    private fun emptyTerm() {
        homeView.visibility = View.GONE
        groupStateView.visibility = View.GONE
    }

    private fun startExecution() {
        loadingView.visibility = View.VISIBLE
        homeView.visibility = View.GONE
        groupStateView.visibility = View.GONE
    }

    private fun finishExecution() {
        loadingView.visibility = View.GONE
    }
}