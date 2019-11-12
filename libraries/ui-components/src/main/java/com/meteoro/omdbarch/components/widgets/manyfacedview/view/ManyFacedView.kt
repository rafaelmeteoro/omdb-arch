package com.meteoro.omdbarch.components.widgets.manyfacedview.view

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.meteoro.omdbarch.components.R
import com.meteoro.omdbarch.components.widgets.manyfacedview.animator.AnimatorComposer

class ManyFacedView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    FrameLayout(context, attrs, defStyle) {

    private lateinit var layoutInflater: LayoutInflater

    private var stateViews: SparseArray<View> = SparseArray()

    @FacedViewState
    private var previousState: Int = FacedViewState.UNKNOWN

    @FacedViewState
    private var initialState: Int = FacedViewState.UNKNOWN

    @FacedViewState
    private var currentState: Int = FacedViewState.UNKNOWN

    private var animateTransition = false
    private var inAnimator: Animator? = null
    private var outAnimator: Animator? = null
    private var animatorComposer: AnimatorComposer? = null

    private var listener: OnStateChangedListener? = null

    init {
        initialize()
        attrs?.let { parseAttributes(it) }
        setupView()
    }

    private fun initialize() {
        layoutInflater = LayoutInflater.from(context)
    }

    private fun parseAttributes(attrs: AttributeSet) {
        val ta = resources.obtainAttributes(attrs, R.styleable.ManyFacedView)
        ta.recycle()
    }

    private fun setupView() {

    }
}