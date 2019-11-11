package com.meteoro.omdbarch.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.components.Disposer
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.navigator.MyNavigator
import dagger.android.AndroidInjection
import io.reactivex.Flowable
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
        val toDispose = Flowable
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