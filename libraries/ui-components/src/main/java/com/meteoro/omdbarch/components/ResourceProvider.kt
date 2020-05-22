package com.meteoro.omdbarch.components

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {

    fun getString(@StringRes resId: Int): String =
        context.getString(resId)

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)
}
