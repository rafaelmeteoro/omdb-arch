package com.meteoro.omdbarch.utilities

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.meteoro.omdbarch.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class Disposer(private val logger: Logger) : DefaultLifecycleObserver {

    private val trash by lazy {
        CompositeDisposable()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        logger.i("Disposing at onDestroy -> ${trash.size()} items")
        trash.clear()
        super.onDestroy(owner)
    }

    fun collect(target: Disposable) {
        trash.add(target)
    }
}