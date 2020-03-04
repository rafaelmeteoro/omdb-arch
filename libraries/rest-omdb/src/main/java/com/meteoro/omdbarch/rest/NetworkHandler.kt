package com.meteoro.omdbarch.rest

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.core.content.ContextCompat
import com.meteoro.omdbarch.domain.services.ConnectivityService

class NetworkHandler(context: Context) : ConnectivityService {

    private val service = ContextCompat.getSystemService(context, ConnectivityManager::class.java)

    override fun isConnected(): Boolean {
        return service?.activeNetworkInfo?.isConnected ?: false
    }

    override fun isWifiConnected(): Boolean {
        return if (isVersionMarshmallow()) {
            val network: Network? = service?.activeNetwork
            val capabilities: NetworkCapabilities? = service?.getNetworkCapabilities(network)
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        } else {
            val networkInfo: NetworkInfo? = service?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            networkInfo?.isConnected ?: false
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun isVersionMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
}