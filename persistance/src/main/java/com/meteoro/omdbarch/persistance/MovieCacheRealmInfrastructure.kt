package com.meteoro.omdbarch.persistance

import com.meteoro.omdbarch.domain.errors.SearchMoviesError.NoResultsFound
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRealm
import com.meteoro.omdbarch.persistance.realm.BuildMovieFromRealm
import com.meteoro.omdbarch.persistance.realm.BuildMovieRealm
import com.meteoro.omdbarch.persistance.realm.RealmManager
import io.reactivex.Observable
import io.realm.kotlin.where

class MovieCacheRealmInfrastructure : MovieCacheService {

    override fun save(movie: Movie) {
        val movieRealm = BuildMovieRealm(movie)
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

    override fun movieCached(imdbId: String): Observable<Movie> {
        return Observable.create { emmiter ->

            val movieRealm = RealmManager.run {
                realm.where<FavoriteMovieRealm>()
                    .equalTo("imdbId", imdbId)
                    .findFirst()
                    ?.let { realm.copyFromRealm(it) }
            }

            if (movieRealm != null) {
                emmiter.onNext(BuildMovieFromRealm(movieRealm))
                emmiter.onComplete()
            } else {
                emmiter.onError(NoResultsFound)
            }
        }
    }

    override fun moviesCached(): Observable<List<Movie>> {
        return Observable.create { emmiter ->

            val moviesRealm = RealmManager.run {
                realm.where<FavoriteMovieRealm>()
                    .findAll()
                    .let { realm.copyFromRealm(it) }
            }

            val converted = Observable.fromIterable(moviesRealm)
                .map { BuildMovieFromRealm(it) }
                .toList()
                .blockingGet()

            emmiter.onNext(converted)
            emmiter.onComplete()
        }
    }
}