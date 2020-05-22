package com.meteoro.omdbarch.domain.repository

import io.reactivex.Flowable

interface ManagerRepository {
    fun save(search: String)
    fun delete(search: String)
    fun fetchSearchList(): Flowable<List<String>>
}
