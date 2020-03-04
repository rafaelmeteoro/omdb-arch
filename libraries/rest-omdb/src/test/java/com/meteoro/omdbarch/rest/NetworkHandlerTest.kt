package com.meteoro.omdbarch.rest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.meteoro.omdbarch.domain.services.ConnectivityService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class NetworkHandlerTest {

    private val context = mock<Context>()
    private val connectivityManager = mock<ConnectivityManager>()
    private val networkInfo = mock<NetworkInfo>()

    private lateinit var service: ConnectivityService

    @Before
    fun `before each test`() {
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
        whenever(connectivityManager.activeNetworkInfo)
            .thenReturn(networkInfo)

        service = NetworkHandler(context)
    }

    @Test
    fun `should return false when ConnectivityManager is null`() {
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(null)

        assertThat(service.isConnected()).isEqualTo(false)
    }

    @Test
    fun `should return false when NetworkInfo is null`() {
        whenever(connectivityManager.activeNetworkInfo)
            .thenReturn(null)

        assertThat(service.isConnected()).isEqualTo(false)
    }

    @Test
    fun `should return false when there is no connection`() {
        whenever(networkInfo.isConnected)
            .thenReturn(false)

        assertThat(service.isConnected()).isEqualTo(false)
    }

    @Test
    fun `should return true when there is connection`() {
        whenever(networkInfo.isConnected)
            .thenReturn(true)

        assertThat(service.isConnected()).isEqualTo(true)
    }
}