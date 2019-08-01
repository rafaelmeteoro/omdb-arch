package com.meteoro.omdbarch.domain

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.EmptyTerm
import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
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
        val testObserver = simpleSearchMovies().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(movies)

        assertThat(simpleSearchMovies().blockingFirst()).isEqualTo(movies)
    }

    @Test
    fun `should search invalide title`() {
        whenever(omdbService.searchMovies(anyString(), anyString(), anyString()))
            .thenReturn(Observable.empty())

        val testObserver = simpleSearchMovies().test()

        testObserver.assertNotComplete()
        testObserver.assertError(NoResultsFound)

        assertThat(testObserver.errors().first()).isEqualTo(NoResultsFound)
    }

    @Test
    fun `should throw with invalid term`() {
        val testObserver = simpleFetchMovie("").test()

        testObserver.assertNotComplete()
        testObserver.assertError(EmptyTerm)

        assertThat(testObserver.errors().first()).isEqualTo(EmptyTerm)
    }

    @Test
    fun `should fetch valid id`() {
        val testObserver = simpleFetchMovie("10").test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(movie)

        assertThat(simpleFetchMovie("10").blockingFirst()).isEqualTo(movie)
    }

    private fun simpleSearchMovies() =
        usecase.search("Avenges", "", "")

    private fun simpleFetchMovie(id: String) =
        usecase.fetch(id)
}