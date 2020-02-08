package com.meteoro.omdbarch.domain.errors

object NoConnectivityError : Throwable() {

    override fun toString(): String {
        return "No internet connection"
    }
}