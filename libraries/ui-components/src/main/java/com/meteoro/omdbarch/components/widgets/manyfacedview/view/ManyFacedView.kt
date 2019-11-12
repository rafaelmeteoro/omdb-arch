package com.meteoro.omdbarch.components.widgets.manyfacedview.view

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
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

        val hasContentView = inflateViewForStateIfExists(ta, FacedViewState.CONTENT, R.styleable.ManyFacedView_mfv_content)
        check(hasContentView) { "Missing view for content" }

        inflateViewForStateIfExists(ta, FacedViewState.EMPTY, R.styleable.ManyFacedView_mfv_empty)
        inflateViewForStateIfExists(ta, FacedViewState.ERROR, R.styleable.ManyFacedView_mfv_error)
        inflateViewForStateIfExists(ta, FacedViewState.LOADING, R.styleable.ManyFacedView_mfv_loading)

        if (ta.hasValue(R.styleable.ManyFacedView_mfv_state)) {
            initialState = ta.getInt(R.styleable.ManyFacedView_mfv_state, FacedViewState.UNKNOWN)
        }

        animateTransition = ta.getBoolean(R.styleable.ManyFacedView_mfv_animateChanges, true)

        val inAnimationId = ta.getResourceId(R.styleable.ManyFacedView_mfv_inAnimation, R.anim.mfv_fade_in)
        inAnimator = AnimatorInflater.loadAnimator(context, inAnimationId)

        val outAnimationId = ta.getResourceId(R.styleable.ManyFacedView_mfv_outAnimation, R.anim.mfv_fade_out)
        outAnimator = AnimatorInflater.loadAnimator(context, outAnimationId)

        ta.recycle()
    }

    private fun setupView() {
        val newState = if (initialState == FacedViewState.UNKNOWN) FacedViewState.CONTENT else initialState
        setState(newState)
    }

    private fun inflateViewForStateIfExists(ta: TypedArray, @FacedViewState state: Int, styleable: Int): Boolean {
        if (ta.hasValue(styleable)) {
            val layoutId = ta.getResourceId(styleable, 0)
            addStateView(state, layoutId)
            return true
        }
        return false
    }

    private fun addStateView(@FacedViewState state: Int, view: View) {
        stateViews.put(state, view)
        if (currentState != state) {
            view.visibility = View.INVISIBLE
        }
        addView(view)
    }

    private fun addStateView(@FacedViewState state: Int, @LayoutRes layoutId: Int) {
        val inflatedView = layoutInflater.inflate(layoutId, this, false)
        addStateView(state, inflatedView)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(state: Int): T {
        return stateViews.get(state) as T
    }

    fun hasStateViewAttached(): Boolean {
        return childCount > 0
    }

    @FacedViewState
    fun getState(): Int {
        return this.currentState
    }

    fun setState(@FacedViewState state: Int) {
        if (currentState == state) {
            return
        }

        previousState = currentState
        currentState = state
    }
}