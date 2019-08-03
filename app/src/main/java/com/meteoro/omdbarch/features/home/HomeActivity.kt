package com.meteoro.omdbarch.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.R
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.Disposer
import io.reactivex.rxkotlin.subscribeBy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModel by instance<HomeViewModel>()
    private val disposer by instance<Disposer>()
    private val logger by instance<Logger>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        searchMovie("Avengers")
    }

    private fun searchMovie(title: String) {
        val toDispose = viewModel
            .searchMovie(title)
            .subscribeBy(
                onNext = { logger.e("Next -> $it") },
                onError = { logger.e("Error -> $it") }
            )

        disposer.collect(toDispose)
    }
}