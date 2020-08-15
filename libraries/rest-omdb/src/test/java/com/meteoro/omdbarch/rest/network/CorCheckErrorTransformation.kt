package com.meteoro.omdbarch.rest.network

import com.meteoro.omdbarch.coroutines.testutils.unwrapError
import com.meteoro.omdbarch.domain.errors.ErrorTransformer
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CorCheckErrorTransformation(
    private val original: Throwable,
    private val transformer: ErrorTransformer
) {
    private fun runCheck(check: (Throwable) -> Unit) =
        runBlocking {
            val result = runCatching { errorAtSuspendableOperation(original) }
            val unwrapped = unwrapError(result)
            val transformed = transformer.transform(unwrapped)
            check(transformed)
        }

    private suspend fun errorAtSuspendableOperation(error: Throwable) =
        suspendCoroutine<Unit> { continuation ->
            continuation.resumeWithException(error)
        }

    companion object {
        fun corCheckTransformation(from: Throwable, using: ErrorTransformer, check: (Throwable) -> Unit) =
            CorCheckErrorTransformation(from, using).runCheck { check.invoke(it) }
    }
}