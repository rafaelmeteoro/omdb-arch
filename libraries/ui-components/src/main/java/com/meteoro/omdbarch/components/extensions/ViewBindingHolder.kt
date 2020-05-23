package com.meteoro.omdbarch.components.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding

/*
 * Holds an manages ViewBinding insise a Fragment
 * */
interface ViewBindingHolder<T : ViewBinding> {

    var binding: T?

    /**
     * Saves the binding for cleanup on OnDestroy, calls the specified function [onBound] with `this` value
     * as its receiver and returns the bound view root.
     * */
    fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)?): View

    /**
     * Calls the specified [block] with the binding as `this`value and returns the binding. As a consequence,
     * this method can be used with a code block lambda in [block] or to initialize a variable with the return type.
     *
     * @throws IllegalStateException if not currently holding a ViewBinding (when called outside of an
     * active fragment's lifecycle)
     * */
    fun requireBinding(block: (T.() -> Unit)? = null): T
}

class ViewBindingHolderImpl<T : ViewBinding> : ViewBindingHolder<T>, LifecycleObserver {

    override var binding: T? = null
    var lifecycle: Lifecycle? = null

    private lateinit var fragmentName: String

    /**
     * To not leak memory we nullify the binding when the view is destroyed.
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyView() {
        lifecycle?.removeObserver(this) // not mandatory, but preferred
        lifecycle = null
        binding = null
    }

    override fun requireBinding(block: (T.() -> Unit)?): T =
        binding?.apply { block?.invoke(this) }
            ?: throw IllegalStateException("Accessing binding outside of Fragment lifecycle: $fragmentName")

    override fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)?): View {
        this.binding = binding
        lifecycle = fragment.viewLifecycleOwner.lifecycle
        lifecycle?.addObserver(this)
        fragmentName = fragment::class.simpleName ?: "N/A"
        onBound?.invoke(binding)
        return binding.root
    }
}
