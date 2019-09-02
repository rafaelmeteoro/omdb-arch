package com.meteoro.omdbarch.favorites.words

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.favorites.R
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.Disposer
import com.meteoro.omdbarch.utilities.ViewState
import com.meteoro.omdbarch.utilities.ViewState.*
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_words.*
import javax.inject.Inject

class WordsFragment : Fragment() {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: WordsViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(disposer)
        getWordsSaved()
    }

    private fun getWordsSaved() {
        val toDispose = viewModel
            .fetchWordsSaved()
            .subscribeBy(
                onNext = { changeState(it) },
                onError = { logger.e("Error -> $it") }
            )

        disposer.collect(toDispose)
    }

    private fun changeState(event: ViewState<WordsPresentation>) {
        when (event) {
            is Launched -> startExecution()
            is Success -> showWords(event.value)
            is Failed -> handleError(event.reason)
            is Done -> finishExecution()
        }
    }

    private fun showWords(presentation: WordsPresentation) {
        logger.d("Filling Content")
        wordsHeadline.visibility = View.VISIBLE

        val words = presentation.words

        ChipsGroupPopulator(historyChipGroup, R.layout.chip_item_word).run {
            populate(words) {
                viewModel.deleteWord(it)
                getWordsSaved()
            }
        }
    }

    private fun handleError(reason: Throwable) {
        logger.e("Failed to load words -> $reason")

        if (reason is NoResultsFound) {
            emptyStateView.visibility = View.VISIBLE
            return
        }

        showErrorReport(R.string.fragment_words_error)
    }

    private fun startExecution() {
        loadingWords.visibility = View.VISIBLE
    }

    private fun finishExecution() {
        loadingWords.visibility = View.GONE
    }

    private fun showErrorReport(targetMessageId: Int) {
        Snackbar
            .make(wordsScreenRoot, targetMessageId, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}