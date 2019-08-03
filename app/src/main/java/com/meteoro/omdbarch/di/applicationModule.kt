package com.meteoro.omdbarch.di

import com.meteoro.omdbarch.logger.LogcatLogger
import com.meteoro.omdbarch.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val applicationModule = Kodein.Module("application") {

    bind<Logger>() with singleton {
        LogcatLogger
    }

    bind() from singleton {
        AndroidSchedulers.mainThread()
    }
}