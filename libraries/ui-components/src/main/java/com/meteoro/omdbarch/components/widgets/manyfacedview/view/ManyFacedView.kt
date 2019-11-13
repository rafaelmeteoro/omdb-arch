package com.meteoro.omdbarch.components.widgets.manyfacedview.view

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.meteoro.omdbarch.components.R
import com.meteoro.omdbarch.components.widgets.manyfacedview.animator.ActionCallback
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

        val hasContentView =
            inflateViewForStateIfExists(ta, FacedViewState.CONTENT, R.styleable.ManyFacedView_mfv_content)
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

        require(stateViews.indexOfKey(state) >= 0) { "Attempting to set state without setting its view ($state)" }

        val inView = stateViews.get(state)
        val outView = stateViews.get(currentState)

        previousState = currentState
        currentState = state

        if (animateTransition) {
            animateViewSwap(outView, inView)
        } else {
            immediateSwap(outView, inView)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun animateViewSwap(outView: View?, inView: View) {
        cancelPreviousAnimation()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            animatePreLollipop(outView, inView)
        } else {
            animateLollipopAndNewer(outView, inView)
        }
    }

    private fun animatePreLollipop(outView: View?, inView: View) {
        if (outView != null) {
            animateInAndOutView(outView, inView)
        } else {
            animateInView(inView)
        }
    }

    private fun animateLollipopAndNewer(outView: View?, inView: View) {
        animateInAndOutView(outView, inView)
    }

    private fun animateInAndOutView(outView: View?, inView: View) {
        inView.visibility = View.INVISIBLE

        animatorComposer = AnimatorComposer
            .from(outAnimator, outView)
            .nextAction(object : ActionCallback {
                override fun execute() {
                    hideOutView(outView)
                    inView.visibility = View.VISIBLE
                }
            })
            .next(inAnimator, inView)
            .nextAction(object : ActionCallback {
                override fun execute() {
                    notifyStateChanged()
                }
            })
            .start()
    }

    private fun animateInView(inView: View) {
        inView.visibility = View.VISIBLE

        animatorComposer = AnimatorComposer
            .from(inAnimator, inView)
            .nextAction(object : ActionCallback {
                override fun execute() {
                    notifyStateChanged()
                }
            })
            .start()
    }

    private fun cancelPreviousAnimation() {
        if (animatorComposer != null) {
            animatorComposer?.stop()
        }
    }

    private fun immediateSwap(outView: View?, inView: View) {
        hideOutView(outView)
        inView.visibility = View.VISIBLE
        notifyStateChanged()
    }

    private fun notifyStateChanged() {
        if (listener != null && previousState != FacedViewState.UNKNOWN) {
            listener?.onChanged(currentState)
        }
    }

    private fun hideOutView(outView: View?) {
        if (outView != null) {
            outView.visibility = View.INVISIBLE
        }
    }

    fun enableTransitionAnimation(enable: Boolean) {
        animateTransition = enable
    }

    fun setInAnimator(inAnimator: Animator) {
        this.inAnimator = inAnimator
    }

    fun setOutAnimator(outAnimator: Animator) {
        this.outAnimator = outAnimator
    }

    fun setOnStateChangedListener(listener: OnStateChangedListener?) {
        this.listener = listener
    }

    override fun onSaveInstanceState(): Parcelable? {
        val parentState = super.onSaveInstanceState()
        val savedState = SavedState(parentState)
        savedState.currentState = currentState
        savedState.previousState = previousState

        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(state.superState)

        currentState = previousState
        setState(savedState.currentState)
    }

    class SavedState : BaseSavedState {

        @FacedViewState
        var currentState: Int = FacedViewState.UNKNOWN

        @FacedViewState
        var previousState: Int = FacedViewState.UNKNOWN

        constructor(superState: Parcelable?) : super(superState)

        private constructor(parcel: Parcel?) : super(parcel) {
            this.currentState = parcel?.readInt() ?: FacedViewState.UNKNOWN
            this.previousState = parcel?.readInt() ?: FacedViewState.UNKNOWN
        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeInt(currentState)
            out?.writeInt(previousState)
        }

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel?) = SavedState(source)
                override fun newArray(size: Int) = arrayOfNulls<SavedState>(size)
            }
        }
    }
}