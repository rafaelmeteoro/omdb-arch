package com.meteoro.omdbarch.components.binding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding

abstract class BindingActivity<ViewBindingType : ViewBinding> : AppCompatActivity(), LifecycleObserver {

    companion object {
        private const val TAG = "BindingActivity"
    }

    // Variables
    private var _binding: ViewBindingType? = null

    // Binding variable to be used for accessing views.
    protected val binding
        get() = requireNotNull(_binding)

    /**
     * Calls the abstract function to return ViewBinding and set up Lifecycle Observer to get
     * rid of binding once done.
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = setupViewBinding(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        lifecycle.addObserver(this)
        init()
    }

    abstract fun setupViewBinding(inflater: LayoutInflater): ViewBindingType

    abstract fun init()

    // Clear the binding and removes the observer when the activity is destroyed.
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun clearViewBinding() {
        _binding = null
        lifecycle.removeObserver(this)
    }

    /**
     * Safe call method, just in case, if anything is messed up and lifecycle event does not get
     * called.
     * */
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
