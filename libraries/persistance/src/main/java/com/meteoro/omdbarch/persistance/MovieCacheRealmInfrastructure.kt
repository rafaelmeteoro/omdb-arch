package com.meteoro.omdbarch.persistance

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRealm
import com.meteoro.omdbarch.persistance.realm.MapperMovieRealm
import com.meteoro.omdbarch.persistance.realm.RealmManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.realm.kotlin.where

class MovieCacheRealmInfrastructure : MovieCacheService {

    override fun save(movie: Movie) {
        val movieRealm = MapperMovieRealm().fromMovie(movie)
        RealmManager.run {
            realm.executeTransaction { it.copyToRealmOrUpdate(movieRealm) }
        }
    }

    override fun delete(movie: Movie) {
        RealmManager.run {
            realm.executeTransaction {
                val movieRealm = it.where<FavoriteMovieRealm>()
                    .equalTo("imdbId", movie.imdbId)
                    .findFirst()

                movieRealm?.deleteFromRealm()
            }
        }
    }

    override fun deleteAll() {
        RealmManager.run {
            realm.executeTransaction { it.deleteAll() }
        }
    }

    override fun movieCached(imdbId: String): Flowable<Movie> {
        return Flowable.create({ emmiter ->
            val movieRealm = RealmManager.run {
                realm.where<FavoriteMovieRealm>()
                    .equalTo("imdbId", imdbId)
                    .findFirst()
                    ?.let { realm.copyFromRealm(it) }
            }

            if (movieRealm != null) {
                emmiter.onNext(MapperMovieRealm().fromRealm(movieRealm))
                emmiter.onComplete()
            } else {
                emmiter.onError(NoResultsFound)
            }
        }, BackpressureStrategy.DROP)
    }

    override fun moviesCached(): Flowable<List<Movie>> {
        return Flowable.create({ emmiter ->
            val moviesRealm = RealmManager.run {
                realm.where<FavoriteMovieRealm>()
                    .findAll()
                    .let { realm.copyFromRealm(it) }
            }

            val converted = Flowable.fromIterable(moviesRealm)
                .map { MapperMovieRealm().fromRealm(it) }
                .toList()
                .blockingGet()

            emmiter.onNext(converted)
            emmiter.onComplete()
        }, BackpressureStrategy.DROP)
    }
}
