package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.mapper.asMovie
import com.meteoro.omdbarch.rest.mapper.asMovies
import io.reactivex.Observable

class MoveisInfrastructure(private val api: OmdbAPI) : RemoteOmdbService {

    override fun searchMovies(title: String, type: String?, year: String?): Observable<List<Movie>> {
        return managedExecution {
            api.getSearch(title, type, year).map {
                it.asMovies()
            }
        }
    }

    override fun fetchMovie(id: String): Observable<Movie> {
        return managedExecution {
            api.getMovie(id).map {
                it.asMovie()
            }
        }
    }
}