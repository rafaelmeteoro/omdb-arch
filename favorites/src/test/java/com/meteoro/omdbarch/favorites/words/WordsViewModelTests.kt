package com.meteoro.omdbarch.favorites.words

import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.utilities.StateMachine
import com.meteoro.omdbarch.utilities.ViewState.Failed
import com.meteoro.omdbarch.utilities.ViewState.Launched
import com.meteoro.omdbarch.utilities.ViewState.Success
import com.meteoro.omdbarch.utilities.ViewState.Done
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class WordsViewModelTests {

    private lateinit var viewModel: WordsViewModel

    private val mockManager = mock<ManagerSearch>()

    private val result = listOf("matrix", "avengers", "marvel")

    @Before
    fun `before each test`() {
        viewModel = WordsViewModel(
            manager = mockManager,
            machine = StateMachine()
        )
    }

    @Test
    fun `should emmit states for successful words presentation`() {
        val expected = BuildWordsPresentation(result)

        whenever(mockManager.fetchSearchList())
            .thenReturn(Observable.just(result))

        viewModel.fetchWordsSaved().test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Success(expected),
                    Done
                )
            )
    }

    @Test
    fun `should emmit states for empty values`() {
        whenever(mockManager.fetchSearchList())
            .thenReturn(Observable.error(NoResultsFound))

        viewModel.fetchWordsSaved().test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    Launched,
                    Failed(NoResultsFound),
                    Done
                )
            )
    }
}