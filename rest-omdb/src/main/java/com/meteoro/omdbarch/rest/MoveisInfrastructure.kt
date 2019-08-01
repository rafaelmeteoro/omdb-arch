package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.mapper.asMovie
import io.reactivex.Observable

class MoveisInfrastructure(private val api: OmdbAPI) : RemoteOmdbService {

    override fun searchMovies(title: String, type: String?, year: String?): Observable<List<Movie>> {
        return Observable.create { emitter ->
            return@create managedExecution {
                val response = api.getSearch(title, type, year).execute()
                if (response.isSuccessful) {
                    val result = response.body()?.search?.map { it.asMovie() } ?: emptyList()
                    emitter.onNext(result)
                    emitter.onComplete()
                } else {
                    emitter.onError(Throwable())
                }
            }
        }
    }

    override fun fetchMovie(id: String): Observable<Movie> {
        return Observable.create {
            return@create managedExecution {
                api.getMovie(id).execute()
            }
        }
    }
}