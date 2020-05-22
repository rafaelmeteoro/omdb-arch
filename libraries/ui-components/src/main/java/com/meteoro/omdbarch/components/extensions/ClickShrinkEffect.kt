package com.meteoro.omdbarch.components.extensions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.MotionEvent
import android.view.View
import java.lang.ref.WeakReference

private const val SHRINK_VALUE = 0.90f
private const val DEFAULT_VALUE = 1f
private const val DURATION = 200L

class ClickShrinkEffect(view: View) {

    private val weakRefView = WeakReference(view)

    init {
        if (!view.hasOnClickListeners()) view.setOnClickListener { }
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> buildShrinkAnimator()?.start()
                MotionEvent.ACTION_UP -> buildGrowAnimator()?.start()
            }
            return@setOnTouchListener false
        }
    }

    private fun buildShrinkAnimator(): Animator? {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, DEFAULT_VALUE, SHRINK_VALUE)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, DEFAULT_VALUE, SHRINK_VALUE)
        weakRefView.get()?.apply {
            val animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
            animator.duration = DURATION
            return animator
        }
        return null
    }

    private fun buildGrowAnimator(): Animator? {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, SHRINK_VALUE, DEFAULT_VALUE)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, SHRINK_VALUE, DEFAULT_VALUE)
        weakRefView.get()?.apply {
            val animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
            animator.duration = DURATION
            return animator
        }
        return null
    }
}

fun View.applyClickShrink(): View {
    return this.apply {
        ClickShrinkEffect(this)
    }
}
