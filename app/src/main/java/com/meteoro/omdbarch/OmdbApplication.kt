package com.meteoro.omdbarch

import android.app.Application
import kotlinx.serialization.UnstableDefault
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

@UnstableDefault
class OmdbApplication : Application(), KodeinAware {

    override val kodein: Kodein = DependenciesSetup(this).container
}