package com.meteoro.omdbarch.domain.services

interface ConnectivityService {
    fun isConnected(): Boolean
    fun isWifiConnected(): Boolean
}
