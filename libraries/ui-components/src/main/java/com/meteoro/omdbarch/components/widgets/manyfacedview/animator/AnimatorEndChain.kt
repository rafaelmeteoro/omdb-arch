package com.meteoro.omdbarch.components.widgets.manyfacedview.animator

import android.animation.Animator

class AnimatorEndChain(private val animator: Animator?) : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {
        animator?.start()
    }
}
