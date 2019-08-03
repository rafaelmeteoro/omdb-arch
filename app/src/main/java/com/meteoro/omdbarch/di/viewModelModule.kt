package com.meteoro.omdbarch.di

import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.features.home.HomeViewModel
import com.meteoro.omdbarch.utilities.StateMachine
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModule = Kodein.Module("viewModelModule") {

    bind() from provider {
        FetchSearch(
            service = instance()
        )
    }

    bind() from provider {
        HomeViewModel(
            fetch = instance(),
            machine = StateMachine(
                uiScheduler = instance()
            )
        )
    }
}