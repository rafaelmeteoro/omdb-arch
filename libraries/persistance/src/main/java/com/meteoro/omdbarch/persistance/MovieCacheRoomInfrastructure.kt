package com.meteoro.omdbarch.persistance

import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.persistance.room.MapperMovieRoom
import com.meteoro.omdbarch.persistance.room.MovieDao
import io.reactivex.Flowable

class MovieCacheRoomInfrastructure(
    private val dao: MovieDao
) : MovieCacheService {

    override fun save(movie: Movie) {
        val movieRoom = MapperMovieRoom().fromMovie(movie)
        dao.insert(movieRoom)
    }

    override fun delete(movie: Movie) {
        val movieRoom = MapperMovieRoom().fromMovie(movie)
        dao.remove(movieRoom)
    }

    override fun deleteAll() {
        dao.clear()
    }

    override fun movieCached(imdbId: String): Flowable<Movie> {
        return dao.favoriteMovie(imdbId)
            .map { MapperMovieRoom().fromRoom(it) }
            .toFlowable() // Convert Maybe to Flowable
    }

    override fun moviesCached(): Flowable<List<Movie>> {
        return dao.allFavoritesMovies()
            .toFlowable() // Convert Maybe to Flowable
            .flatMap { movies ->
                Flowable.fromIterable(movies)
                    .map { MapperMovieRoom().fromRoom(it) }
                    .toList()
                    .toFlowable()
            }
    }
}
