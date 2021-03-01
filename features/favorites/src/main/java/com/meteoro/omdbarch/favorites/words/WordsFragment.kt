package com.meteoro.omdbarch.favorites.words

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.meteoro.omdbarch.components.binding.BindingFragment
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.state.ViewState
import com.meteoro.omdbarch.favorites.R
import com.meteoro.omdbarch.favorites.databinding.FragmentWordsBinding
import com.meteoro.omdbarch.favorites.databinding.StateWordsContentBinding
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class WordsFragment : BindingFragment<FragmentWordsBinding>() {

    @Inject
    lateinit var disposer: Disposer

    @Inject
    lateinit var viewModel: WordsViewModel

    private lateinit var bindingContent: StateWordsContentBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentWordsBinding {
        val fragmentWords = FragmentWordsBinding.inflate(inflater, container, false)
        bindingContent = StateWordsContentBinding.bind(fragmentWords.stateView.getView(FacedViewState.CONTENT))
        return fragmentWords
    }

    override fun init() {
        getWordsSaved()
    }

    private fun getWordsSaved() {
        val toDispose = viewModel
            .fetchWordsSaved()
            .subscribeBy(
                onNext = { changeState(it) },
                onError = { Timber.e("Error -> $it") }
            )

        disposer.collect(toDispose)
    }

    private fun changeState(event: ViewState<WordsPresentation>) {
        when (event) {
            is ViewState.Launched -> startExecution()
            is ViewState.Success -> showWords(event.value)
            is ViewState.Failed -> handleError(event.reason)
            is ViewState.Done -> Unit
        }
    }

    private fun showWords(presentation: WordsPresentation) {
        Timber.d("${presentation.words}")
        binding.stateView.setState(FacedViewState.CONTENT)

        val words = presentation.words

        ChipsGroupPopulator(bindingContent.historyChipGroup, R.layout.chip_item_word).run {
            populate(words) {
                viewModel.deleteWord(it)
                getWordsSaved()
            }
        }
    }

    private fun handleError(reason: Throwable) {
        Timber.e("Failed to load words -> $reason")

        if (reason is NoResultsFound) {
            binding.stateView.setState(FacedViewState.EMPTY)
            return
        }

        binding.stateView.setState(FacedViewState.ERROR)
        showErrorReport(R.string.fragment_words_error)
    }

    private fun startExecution() {
        binding.stateView.setState(FacedViewState.LOADING)
    }

    private fun showErrorReport(targetMessageId: Int) {
        binding.let {
            Snackbar
                .make(it.wordsScreenRoot, targetMessageId, Snackbar.LENGTH_INDEFINITE)
                .show()
        }
    }
}
