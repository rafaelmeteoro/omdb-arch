package com.meteoro.omdbarch.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.navigator.MyNavigator
import com.meteoro.omdbarch.utilities.Disposer
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OnboardingActivity : AppCompatActivity() {

    companion object {
        private const val TIMER_COUNT = 1000L
    }

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var navigator: MyNavigator

    @Inject
    lateinit var disposer: Disposer

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        lifecycle.addObserver(disposer)

        logger.d("Launch")
        handleLaunch()
    }

    private fun handleLaunch() {
        val toDispose = Observable
            .timer(TIMER_COUNT, TimeUnit.MILLISECONDS)
            .subscribeBy(
                onComplete = { goToHome() }
            )

        disposer.collect(toDispose)
    }

    private fun goToHome() {
        navigator.navigateToHome(this)
    }
}