package com.meteoro.omdbarch.rest.executor

import com.meteoro.omdbarch.domain.errors.NetworkingError
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.ConnectivityService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

class RemoteExecutorImplTest {

    private val service = mock<ConnectivityService>()

    private lateinit var executor: RemoteExecutor

    @Before
    fun `before each test`() {
        executor = RemoteExecutorImpl(service)
    }

    @Test
    fun `should throw NoConnectivityError when there is no internet connection`() {
        whenever(service.isConnected())
            .thenReturn(false)

        val expected = NetworkingError.NoInternetConnection

        val testSubscriber = TestSubscriber<Movie>()
        executor.checkConnectionAndThanFlowable(
            action = Flowable.just(Movie())
        ).subscribe(testSubscriber)

        testSubscriber.assertNotComplete()
            .assertTerminated()
            .assertError(expected)
    }

    @Test
    fun `should report crash when is any exception`() {
        whenever(service.isConnected())
            .thenReturn(true)

        val expected = Throwable("this is an exception")

        val testSubscriber = TestSubscriber<String>()
        executor.checkConnectionAndThanFlowable(
            action = Flowable.error<String>(expected)
        ).subscribe(testSubscriber)

        testSubscriber.assertNotComplete()
            .assertTerminated()
            .assertError(expected)
    }

    @Test
    fun `should API call get a flowable when is internet connection`() {
        whenever(service.isConnected())
            .thenReturn(true)

        val response = Movie()

        val testSubscriber = TestSubscriber<Movie>()
        executor.checkConnectionAndThanFlowable(
            action = Flowable.just(response)
        ).subscribe(testSubscriber)

        testSubscriber.assertComplete()
            .assertNoErrors()
            .assertValue(response)
    }
}