package com.meteoro.omdbarch.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.actions.Actions
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.onboarding.databinding.ActivityOnboardingBinding
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
    lateinit var disposer: Disposer

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        startActivity(Actions.openHomeIntent(this))
        finish()
    }
}