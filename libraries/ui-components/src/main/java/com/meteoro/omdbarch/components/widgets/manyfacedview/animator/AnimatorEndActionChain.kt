package com.meteoro.omdbarch.components.widgets.manyfacedview.animator

import android.animation.Animator

@Suppress("EmptyFunctionBlock")
class AnimatorEndActionChain(private val action: ActionCallback?) : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {
        action?.execute()
    }
}
