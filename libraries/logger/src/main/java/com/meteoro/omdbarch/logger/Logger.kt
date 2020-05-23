package com.meteoro.omdbarch.logger

@Suppress("FunctionMinLength")
interface Logger {
    fun v(message: String)
    fun d(message: String)
    fun i(message: String)
    fun w(message: String)
    fun e(message: String)
}
