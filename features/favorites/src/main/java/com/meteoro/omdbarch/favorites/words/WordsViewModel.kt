package com.meteoro.omdbarch.favorites.words

import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import io.reactivex.Flowable

class WordsViewModel(
    private val manager: ManagerSearch,
    private val machine: StateMachine<WordsPresentation>
) {
    fun fetchWordsSaved(): Flowable<ViewState<WordsPresentation>> =
        manager.fetchSearchList()
            .map { BuildWordsPresentation(it) }
            .compose(machine)

    fun deleteWord(word: String) =
        manager.delete(word)
}