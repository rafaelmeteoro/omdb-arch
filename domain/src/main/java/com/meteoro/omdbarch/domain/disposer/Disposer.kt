package com.meteoro.omdbarch.domain.disposer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class Disposer : DefaultLifecycleObserver {

    private val trash by lazy {
        CompositeDisposable()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Timber.i("Disposing at onDestroy -> ${trash.size()} items")
        trash.clear()
        super.onDestroy(owner)
    }

    fun collect(target: Disposable) {
        trash.add(target)
    }
}
