package com.meteoro.omdbarch.favorites.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.components.decoration.GridItemDecoration
import com.meteoro.omdbarch.components.delegate.autoCleaned
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.state.ViewState
import com.meteoro.omdbarch.favorites.R
import com.meteoro.omdbarch.favorites.databinding.FragmentMovieListBinding
import com.meteoro.omdbarch.favorites.databinding.StateListContentBinding
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MovieListFragment : Fragment(), MovieListAdapter.MovieListListener {

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: MovieListViewModel

    private var binding: FragmentMovieListBinding by autoCleaned()
    private var bindingContent: StateListContentBinding by autoCleaned()
    private val movieListAdapter: MovieListAdapter by autoCleaned { MovieListAdapter(this) }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        bindingContent = StateListContentBinding.bind(binding.stateView.getView(FacedViewState.CONTENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupView()
        getMoviesSaved()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_movie_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.movies_delete_all -> {
            viewModel.deleteAll()
            getMoviesSaved()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        bindingContent.movieListView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(
                GridItemDecoration(
                    space = resources.getDimensionPixelSize(R.dimen.movies_list_spacing),
                    noOfColumns = 1
                )
            )
            adapter = movieListAdapter
        }
    }

    private fun getMoviesSaved() {
        val toDispose = viewModel
            .fetchMoviesSaved()
            .subscribeBy(
                onNext = { changeState(it) },
                onError = { Timber.e("Error -> $it") }
            )

        disposer.collect(toDispose)
    }

    private fun changeState(event: ViewState<MovieListPresentation>) {
        when (event) {
            is ViewState.Launched -> startExecution()
            is ViewState.Success -> handlePresentation(event.value)
            is ViewState.Failed -> handleError(event.reason)
            is ViewState.Done -> Unit
        }
    }

    private fun handlePresentation(presentation: MovieListPresentation) {
        Timber.d("${presentation.movies}")

        binding.stateView.setState(FacedViewState.CONTENT)
        movieListAdapter.setData(presentation)
    }

    private fun handleError(reason: Throwable) {
        Timber.e("Failed to load movies -> $reason")

        if (reason is NoResultsFound) {
            binding.stateView.setState(FacedViewState.EMPTY)
            return
        }

        binding.stateView.setState(FacedViewState.ERROR)
        showErrorReport(R.string.fragment_movie_list_error)
    }

    private fun startExecution() {
        binding.stateView.setState(FacedViewState.LOADING)
    }

    private fun showErrorReport(targetMessageId: Int) {
        binding.let {
            Snackbar
                .make(it.moviesListScreenRoot, targetMessageId, Snackbar.LENGTH_INDEFINITE)
                .show()
        }
    }

    override fun navigateToMovie(movie: Movie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
        action.imdbIdArg = movie.imdbId
        findNavController().navigate(action)
    }

    override fun deleteMovie(movie: Movie) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.fragment_movie_list_delete_title)
            .setMessage(R.string.fragment_movie_list_delete_message)
            .setPositiveButton(R.string.fragment_movie_list_delete_positive) { dialog, _ ->
                viewModel.deleteMovie(movie)
                getMoviesSaved()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.fragment_movie_list_delete_negative, null)
            .show()
    }
}
