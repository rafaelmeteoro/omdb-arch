package com.meteoro.omdbarch.favorites.words

import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class WordsViewModelTest {

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
            .thenReturn(Flowable.just(result))

        viewModel.fetchWordsSaved().test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    ViewState.Launched,
                    ViewState.Success(expected),
                    ViewState.Done
                )
            )

        verify(mockManager, times(timeInvocation())).fetchSearchList()
        verify(mockManager, never()).delete(anyString())
    }

    @Test
    fun `should emmit states for empty values`() {
        whenever(mockManager.fetchSearchList())
            .thenReturn(Flowable.error(NoResultsFound))

        viewModel.fetchWordsSaved().test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    ViewState.Launched,
                    ViewState.Failed(NoResultsFound),
                    ViewState.Done
                )
            )

        verify(mockManager, times(timeInvocation())).fetchSearchList()
        verify(mockManager, never()).delete(anyString())
    }

    @Test
    fun `should call delete in manger`() {
        val mockValue = "avengers"

        viewModel.deleteWord(mockValue)

        verify(mockManager, times(timeInvocation())).delete(mockValue)
        verify(mockManager, never()).fetchSearchList()
    }

    private fun timeInvocation() = 1
}