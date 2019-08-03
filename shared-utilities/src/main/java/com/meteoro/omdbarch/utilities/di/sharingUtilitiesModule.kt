package com.meteoro.omdbarch.utilities.di

import com.meteoro.omdbarch.utilities.Disposer
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val sharedingUtilitiesModule = Kodein.Module("shared-utilities") {

    bind() from provider {
        Disposer(
            logger = instance()
        )
    }
}