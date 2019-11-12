package com.meteoro.omdbarch.components.widgets.manyfacedview.view

import androidx.annotation.IntDef
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState.Companion.CONTENT
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState.Companion.EMPTY
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState.Companion.ERROR
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState.Companion.LOADING
import com.meteoro.omdbarch.components.widgets.manyfacedview.view.FacedViewState.Companion.UNKNOWN

@Target(AnnotationTarget.TYPE, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@IntDef(UNKNOWN, CONTENT, LOADING, ERROR, EMPTY)
@Retention(AnnotationRetention.SOURCE)
annotation class FacedViewState {
    companion object {
        const val UNKNOWN = 0
        const val CONTENT = 1
        const val LOADING = 2
        const val ERROR = 3
        const val EMPTY = 4
    }
}