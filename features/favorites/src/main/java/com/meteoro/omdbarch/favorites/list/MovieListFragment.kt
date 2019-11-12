package com.meteoro.omdbarch.favorites.list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.components.Disposer
import com.meteoro.omdbarch.components.decoration.GridItemDecoration
import com.meteoro.omdbarch.components.ViewState
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.favorites.R
import com.meteoro.omdbarch.logger.Logger
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : Fragment(), MovieListAdapter.MovieListLitener {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: MovieListViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(disposer)
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
        movieListView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(
                GridItemDecoration(
                    space = resources.getDimensionPixelSize(R.dimen.movies_list_spacing),
                    noOfColumns = 1
                )
            )
        }
    }

    private fun getMoviesSaved() {
        val toDispose = viewModel
            .fetchMoviesSaved()
            .subscribeBy(
                onNext = { changeState(it) },
                onError = { logger.e("Error -> $it") }
            )

        disposer.collect(toDispose)
    }

    private fun changeState(event: ViewState<MovieListPresentation>) {
        when (event) {
            is ViewState.Launched -> startExecution()
            is ViewState.Success -> handlePresentation(event.value)
            is ViewState.Failed -> handleError(event.reason)
            is ViewState.Done -> finishExecution()
        }
    }

    private fun handlePresentation(presentation: MovieListPresentation) {
        logger.d("${presentation.movies}")

        movieListView.visibility = View.VISIBLE
        movieListView.adapter = MovieListAdapter(presentation, this)
    }

    private fun handleError(reason: Throwable) {
        logger.e("Failed to load movies -> $reason")

        if (reason is NoResultsFound) {
            movieListView.visibility = View.GONE
            groupStateView.visibility = View.VISIBLE
            return
        }

        showErrorReport(R.string.fragment_movie_list_error)
    }

    private fun startExecution() {
        loadingMovies.visibility = View.VISIBLE
        groupStateView.visibility = View.GONE
    }

    private fun finishExecution() {
        loadingMovies.visibility = View.GONE
    }

    private fun showErrorReport(targetMessageId: Int) {
        Snackbar
            .make(moviesListScreenRoot, targetMessageId, Snackbar.LENGTH_INDEFINITE)
            .show()
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