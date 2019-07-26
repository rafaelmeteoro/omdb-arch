package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.domain.errors.ErrorTransformer

class CheckErrorTransformation(
    private val original: Throwable,
    private val transformer: ErrorTransformer
) {
    private fun runCheck(check: (Throwable) -> Unit) {
        val transformed = transformer.transform(original)
        check(transformed)
    }

    companion object {
        fun checkTransformation(from: Throwable, using: ErrorTransformer, check: (Throwable) -> Unit) =
            CheckErrorTransformation(from, using).runCheck { check.invoke(it) }
    }
}