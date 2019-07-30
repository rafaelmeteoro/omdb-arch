package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class FetchMoviesTests {

    private val omdbService = mock<RemoteOmdbService>()
    private lateinit var usecase: FetchMovies

    private val movies by lazy {
        listOf(
            Movie(
                title = "Movie title",
                year = "2019"
            )
        )
    }

    private val movie by lazy {
        Movie(
            title = "Movie title",
            year = "2019"
        )
    }

    @Before
    fun `before each test`() {
        usecase = FetchMovies(omdbService)

        whenever(omdbService.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Observable.just(movies))
        whenever(omdbService.fetchMovie(anyString()))
            .thenReturn(Observable.just(movie))
    }

    @Test
    fun `should search valid title`() {
        val testObserver = usecase.search("Avengers", "", "").test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(movies)
    }

    @Test
    fun `should search invalide title`() {
        whenever(omdbService.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Observable.empty())

        val testObserver = usecase.search("Avengers", "", "").test()

        testObserver.assertNotComplete()
        testObserver.assertError(SearchMoviesError.NoResultsFound)
    }

    @Test
    fun `should throw with invalid term`() {
        val testObserver = usecase.search("").test()

        testObserver.assertNotComplete()
        testObserver.assertError(SearchMoviesError.EmptyTerm)
    }

    @Test
    fun `should fetch valid id`() {
        val testObserver = usecase.fetch("10").test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(movie)
    }

    @Test
    fun `should throw with invalid id`() {
        val testObserver = usecase.fetch("").test()

        testObserver.assertNotComplete()
        testObserver.assertError(SearchMoviesError.EmptyTerm)
    }
}