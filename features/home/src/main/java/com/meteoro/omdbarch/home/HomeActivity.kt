package com.meteoro.omdbarch.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.actions.Actions
import com.meteoro.omdbarch.components.ErrorStateResources
import com.meteoro.omdbarch.components.decoration.GridItemDecoration
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState
import com.meteoro.omdbarch.domain.connectivity.base.ConnectivityProvider
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.state.ViewState
import com.meteoro.omdbarch.home.databinding.ActivityHomeBinding
import com.meteoro.omdbarch.home.databinding.StateHomeContentBinding
import com.meteoro.omdbarch.home.databinding.StateHomeErrorBinding
import com.meteoro.omdbarch.logger.Logger
import dagger.android.AndroidInjection
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), ConnectivityProvider.ConnectivityStateListener {

    companion object {
        private const val COLUMN_COUNT = 3
        private const val DEBOUNCE_TIME = 750L
    }

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: HomeViewModel

    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(this) }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var bindingContent: StateHomeContentBinding
    private lateinit var bindingError: StateHomeErrorBinding

    private val searchSubject = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        bindingContent = StateHomeContentBinding.bind(getHomeView())
        bindingError = StateHomeErrorBinding.bind(getErrorView())
        setContentView(binding.root)
        lifecycle.addObserver(disposer)

        setupView()
        setupSubject()
    }

    override fun onStart() {
        super.onStart()
        provider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        provider.removeListener(this)
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        val hasInternet = state.hasInternet()
        Snackbar.make(binding.homeRoot, "Connectivity (via callback): $hasInternet", Snackbar.LENGTH_SHORT).show()
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
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
            startActivity(Actions.openFavoritesIntent(this))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun getHomeView() = binding.stateView.getView<RecyclerView>(FacedViewState.CONTENT)
    private fun getErrorView() = binding.stateView.getView<View>(FacedViewState.ERROR)

    private fun setupView() {
        bindingContent.homeView.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, COLUMN_COUNT)
            addItemDecoration(
                GridItemDecoration(
                    space = resources.getDimensionPixelSize(R.dimen.movies_list_spacing),
                    noOfColumns = COLUMN_COUNT
                )
            )
        }

        setSupportActionBar(binding.toolbar)
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
            is ViewState.Done -> Unit
        }
    }

    private fun showMovies(home: HomePresentation) {
        logger.i("Loaded Movies")

        binding.stateView.setState(FacedViewState.CONTENT)
        bindingContent.homeView.adapter = MoviesAdapter(home) {
            startActivity(Actions.openDetailIntent(this, it.imdbId))
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
            bindingContent.homeView.adapter
                ?.let { it.itemCount != 0 }
                ?: false

        when {
            hasPreviousContent -> {
                Snackbar.make(binding.homeRoot, errorMessage, Snackbar.LENGTH_LONG).show()
                emptyTerm()
            }
            else -> reportError(errorImage, errorMessage)
        }
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