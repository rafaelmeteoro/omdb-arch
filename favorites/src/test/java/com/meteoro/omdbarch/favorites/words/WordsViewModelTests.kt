package com.meteoro.omdbarch.favorites.words

import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.utilities.StateMachine
import com.meteoro.omdbarch.utilities.ViewState.*
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

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
        val timeInvocation = 1

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

        verify(mockManager, times(timeInvocation)).fetchSearchList()
        verify(mockManager, never()).delete(anyString())
    }

    @Test
    fun `should emmit states for empty values`() {
        val timeInvocation = 1

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

        verify(mockManager, times(timeInvocation)).fetchSearchList()
        verify(mockManager, never()).delete(anyString())
    }

    @Test
    fun `should call delete in manger`() {
        val mockValue = "avengers"
        val timeInvocation = 1

        viewModel.deleteWord(mockValue)

        verify(mockManager, times(timeInvocation)).delete(mockValue)
        verify(mockManager, never()).fetchSearchList()
    }
}