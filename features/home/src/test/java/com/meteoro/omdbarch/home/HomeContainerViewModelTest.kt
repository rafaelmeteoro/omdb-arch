package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.architecture.StateContainer
import com.meteoro.omdbarch.architecture.StateMachineContainer
import com.meteoro.omdbarch.architecture.TaskExecutor
import com.meteoro.omdbarch.architecture.ViewState
import com.meteoro.omdbarch.coroutines.testutils.CoroutinesTestHelper
import com.meteoro.omdbarch.coroutines.testutils.FlowTest.Companion.flowTest
import com.meteoro.omdbarch.domain.errors.RemoteServiceIntegrationError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.model.ResultSearch
import com.meteoro.omdbarch.domain.model.TypeMovie
import com.meteoro.omdbarch.domain.repository.CorSearchRepository
import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HomeContainerViewModelTest {

    @get:Rule
    val helper = CoroutinesTestHelper()

    private val mockFetch = mock<CorSearchRepository>()
    private val mockManager = mock<ManagerRepository>()

    private lateinit var viewModel: HomeContainerViewModel

    @Before
    fun `before each test`() {
        val stateMachine = StateMachineContainer<HomePresentation>(
            executor = TaskExecutor.Synchronous(helper.scope),
            container = StateContainer.Unbounded(helper.scope)
        )

        viewModel = HomeContainerViewModel(mockFetch, mockManager, stateMachine)
    }

    @Test
    fun `should report failure when fetching from remote`() {
        // Given
        flowTest(viewModel.bind()) {
            triggerEmissions {
                // When
                whenever(mockFetch.searchMovies(any(), any(), any()))
                    .thenAnswer { throw RemoteServiceIntegrationError.UnexpectedResponse }

                // And
                viewModel.handle(SearchQuery("Avengers"))
            }

            afterCollect { emmissions ->
                // Then
                val viewStates = listOf(
                    ViewState.FirstLaunch,
                    ViewState.Loading.FromEmpty,
                    ViewState.Failed(RemoteServiceIntegrationError.UnexpectedResponse)
                )

                assertThat(emmissions).isEqualTo(viewStates)
            }
        }
    }

    @Test
    fun `should fetch movie from remote with success`() {
        val searchResult = ResultSearch(
            search = listOf(
                Movie(
                    title = "Avengers",
                    poster = "https://www.avengers/movie",
                    imdbId = "idavengers"
                )
            ),
            response = "True",
            totalResults = 1
        )

        val presentation = HomePresentation(
            movies = listOf(
                MoviePresentation(
                    title = "Avengers",
                    photoUrl = "https://www.avengers/movie",
                    imdbId = "idavengers",
                    type = TypeMovie.OUTRO
                )
            ),
            response = "True",
            totalResults = 1
        )

        // Given
        flowTest(viewModel.bind()) {
            triggerEmissions {
                // When
                whenever(mockFetch.searchMovies(any(), any(), any())).thenReturn(searchResult)

                // And
                viewModel.handle(SearchQuery("Avengers"))
            }

            afterCollect { emissions ->
                // then
                val viewStates = listOf(
                    ViewState.FirstLaunch,
                    ViewState.Loading.FromEmpty,
                    ViewState.Success(presentation)
                )

                assertThat(emissions).isEqualTo(viewStates)
            }
        }
    }
}