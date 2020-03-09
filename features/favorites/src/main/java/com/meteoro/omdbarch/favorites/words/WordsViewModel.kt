package com.meteoro.omdbarch.favorites.words

import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import io.reactivex.Flowable

class WordsViewModel(
    private val managerRepository: ManagerRepository,
    private val machine: StateMachine<WordsPresentation>
) {
    fun fetchWordsSaved(): Flowable<ViewState<WordsPresentation>> =
        managerRepository.fetchSearchList()
            .map { WordsPresentationMapper().fromDomain(it) }
            .compose(machine)

    fun deleteWord(word: String) =
        managerRepository.delete(word)
}