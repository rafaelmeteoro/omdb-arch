package com.meteoro.omdbarch.components.widgets.manyfacedview.animator

import android.animation.Animator
import android.view.View

class AnimatorComposer private constructor(startingAnimator: Animator?) {

    private var firstAnimator: Animator? = startingAnimator
    private var latestAnimator: Animator? = startingAnimator
    var registeredAnimators: ArrayList<Animator?> = ArrayList()

    init {
        registeredAnimators.add(startingAnimator)
    }

    companion object {
        fun from(startingAnimator: Animator?): AnimatorComposer {
            return AnimatorComposer(startingAnimator)
        }

        fun from(startingAnimator: Animator?, target: View?): AnimatorComposer {
            startingAnimator?.setTarget(target)
            return AnimatorComposer(startingAnimator)
        }
    }

    fun next(nextAnimator: Animator?): AnimatorComposer {
        return next(nextAnimator, null)
    }

    fun next(nextAnimator: Animator?, target: View?): AnimatorComposer {
        if (target != null) {
            nextAnimator?.setTarget(target)
        }
        latestAnimator?.addListener(AnimatorEndChain(nextAnimator))
        latestAnimator = nextAnimator
        registeredAnimators.add(nextAnimator)
        return this
    }

    fun nextAction(action: ActionCallback?): AnimatorComposer {
        latestAnimator?.addListener(AnimatorEndActionChain(action))
        return this
    }

    fun start(): AnimatorComposer {
        nextAction(object : ActionCallback {
            override fun execute() {
                clearListeners()
            }
        })

        firstAnimator?.start()
        return this
    }

    private fun clearListeners() {
        for (anim in registeredAnimators) {
            anim?.listeners?.clear()
        }
    }

    fun stop() {
        for (anim in registeredAnimators) {
            anim?.cancel()
        }
    }
}