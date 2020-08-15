package com.meteoro.omdbarch.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.actions.Actions
import com.meteoro.omdbarch.architecture.ViewState
import com.meteoro.omdbarch.components.ErrorStateResources
import com.meteoro.omdbarch.components.decoration.GridItemDecoration
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState
import com.meteoro.omdbarch.domain.connectivity.base.ConnectivityProvider
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.home.databinding.ActivityHomeBinding
import com.meteoro.omdbarch.home.databinding.StateHomeContentBinding
import com.meteoro.omdbarch.home.databinding.StateHomeErrorBinding
import dagger.android.AndroidInjection
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), ConnectivityProvider.ConnectivityStateListener {

    companion object {
        private const val COLUMN_COUNT = 3
        private const val DEBOUNCE_TIME = 750L
    }

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var viewModelContainer: HomeContainerViewModel

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
            setOnQueryTextListener(
                DebounceQueryTextListener(
                    debouncePeriod = DEBOUNCE_TIME,
                    lifecycle = this@HomeActivity.lifecycle
                ) { newText ->
                    viewModelContainer.handle(SearchQuery(newText))
                }
            )
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
        lifecycleScope.launch {
            viewModelContainer.bind().collect { renderState(it) }
        }
    }

    private fun renderState(state: ViewState<HomePresentation>) {
        when (state) {
            is ViewState.FirstLaunch -> Unit
            is ViewState.Loading.FromEmpty -> startExecution()
            is ViewState.Loading.FromPrevious -> showMovies(state.previous)
            is ViewState.Success -> showMovies(state.value)
            is ViewState.Failed -> handleError(state.reason)
            else -> Unit
        }
    }

    private fun showMovies(home: HomePresentation) {
        Timber.i("Loaded Movies")

        binding.stateView.setState(FacedViewState.CONTENT)
        bindingContent.homeView.adapter = MoviesAdapter(home) {
            startActivity(Actions.openDetailIntent(this, it.imdbId))
        }
    }

    private fun handleError(reason: Throwable) {
        Timber.e("Error -> $reason")

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
