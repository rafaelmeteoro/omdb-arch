package com.meteoro.omdbarch.rest.di

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RestRouter @Inject constructor(
    val requestComponentProvider: Provider<RestComponent.Builder>
) {
    fun moduleReceived(module: RestModule) {
        requestComponentProvider.get()
            .restBuilder(module)
            .build()
    }
}