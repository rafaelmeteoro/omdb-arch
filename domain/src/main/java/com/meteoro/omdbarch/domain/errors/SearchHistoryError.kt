package com.meteoro.omdbarch.domain.errors

object SearchHistoryError : Throwable() {

    override fun toString(): String {
        return "Error when accessing local search"
    }
}