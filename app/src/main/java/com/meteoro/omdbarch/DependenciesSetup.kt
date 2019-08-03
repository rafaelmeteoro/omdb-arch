package com.meteoro.omdbarch

import android.app.Application
import com.meteoro.omdbarch.di.applicationModule
import com.meteoro.omdbarch.di.viewModelModule
import com.meteoro.omdbarch.networking.di.networkingModule
import com.meteoro.omdbarch.rest.di.restingModule
import com.meteoro.omdbarch.utilities.di.sharedingUtilitiesModule
import kotlinx.serialization.UnstableDefault
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

@UnstableDefault
class DependenciesSetup(private val app: Application) {

    val container by lazy {
        ConfigurableKodein(mutable = true).apply {

            modules.forEach { addImport(it) }

            addConfig {
                bind() from singleton { app }
            }
        }
    }

    private val modules = listOf(
        applicationModule,
        networkingModule,
        restingModule,
        sharedingUtilitiesModule,
        viewModelModule
    )
}