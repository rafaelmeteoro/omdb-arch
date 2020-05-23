package com.meteoro.omdbarch.details

import com.meteoro.omdbarch.components.ResourceProvider
import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.repository.MovieRepository
import com.meteoro.omdbarch.domain.state.StateMachine
import com.meteoro.omdbarch.domain.state.ViewState
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

internal class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModelContract

    private val mockFetch = mock<MovieRepository>()
    private val mockCache = mock<CacheRepository>()
    private val mockProvider = mock<ResourceProvider>()

    private val timeInvocation = 1

    private val result = Movie(
        title = "Avengers",
        year = "2012",
        imdbRating = "10",
        poster = "https://www.avengers/movie",
        director = "Russos",
        actors = "Chris",
        plot = "Movie"
    )

    @Before
    fun `before each test`() {
        viewModel = DetailViewModel(
            movieRepository = mockFetch,
            cacheRepository = mockCache,
            machine = StateMachine(),
            resProvider = mockProvider
        )
    }

    @Test
    fun `should emmit states for succesful detail presentation`() {
        whenever(mockProvider.getString(anyInt(), anyString()))
            .thenReturn("")

        val expected = MovieDetailPresentationMapper().fromDomain(result, mockProvider)

        whenever(mockFetch.fetchMovie(anyString()))
            .thenReturn(Flowable.just(result))

        viewModel.fetchMovie("10").test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    ViewState.Launched,
                    ViewState.Success(expected),
                    ViewState.Done
                )
            )

        verify(mockCache, times(timeInvocation)).saveMovie(result)
    }

    @Test
    fun `should emmit states for errored broking integration`() {
        whenever(mockFetch.fetchMovie(anyString()))
            .thenReturn(Flowable.error(NetworkingError.ConnectionSpike))

        viewModel.fetchMovie("10").test()
            .assertComplete()
            .assertValueSequence(
                listOf(
                    ViewState.Launched,
                    ViewState.Failed(NetworkingError.ConnectionSpike),
                    ViewState.Done
                )
            )

        verify(mockCache, never()).saveMovie(any())
    }
}
