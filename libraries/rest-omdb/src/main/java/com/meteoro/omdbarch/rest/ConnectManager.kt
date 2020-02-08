package com.meteoro.omdbarch.rest

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.meteoro.omdbarch.domain.services.ConnectivityService

class ConnectManager private constructor(private val connMgr: ConnectivityManager?) : ConnectivityService {

    override fun isConnected(): Boolean {
        if (connMgr == null) return false
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    override fun isWifiConnected(): Boolean {
        if (connMgr == null) return false

        return if (isVersionMashmallow()) {
            val network: Network? = connMgr.activeNetwork
            val capabilities: NetworkCapabilities? = connMgr.getNetworkCapabilities(network)
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        } else {
            val networkInfo: NetworkInfo? = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            networkInfo?.isConnected == true
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun isVersionMashmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    class Builder(private val context: Context?) {
        fun build(): ConnectManager {
            return ConnectManager(context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)
        }
    }
}