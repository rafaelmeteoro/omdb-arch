package com.meteoro.omdbarch.persistance

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.persistance.room.BuildMovieFromRoom
import com.meteoro.omdbarch.persistance.room.BuildMovieRoom
import com.meteoro.omdbarch.persistance.room.MovieDao
import io.reactivex.Observable

class MovieCacheRoomInfrastructure(
    private val dao: MovieDao
) : MovieCacheService {

    override fun save(movie: Movie) {
        val movieRoom = BuildMovieRoom(movie)
        dao.insert(movieRoom)
    }

    override fun delete(movie: Movie) {
        val movieRoom = BuildMovieRoom(movie)
        dao.remove(movieRoom)
    }

    override fun movieCached(imdbId: String): Observable<Movie> {
        return dao.favoriteMovie(imdbId)
            .map { BuildMovieFromRoom(it) }
            .toObservable() // Convert Maybe to Observable
    }

    override fun moviesCached(): Observable<List<Movie>> {
        return dao.allFavoritesMovies()
            .toObservable() // Convert Maybe to Observable
            .flatMap { movies ->
                Observable.fromIterable(movies)
                    .map { BuildMovieFromRoom(it) }
                    .toList()
                    .toObservable()
            }
    }
}