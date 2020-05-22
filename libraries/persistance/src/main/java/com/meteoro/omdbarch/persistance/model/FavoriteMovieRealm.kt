package com.meteoro.omdbarch.persistance.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FavoriteMovieRealm(
    @PrimaryKey
    var imdbId: String = "",
    var title: String? = null,
    var year: String? = null,
    var released: String? = null,
    var runtime: String? = null,
    var genre: String? = null,
    var director: String? = null,
    var actors: String? = null,
    var plot: String? = null,
    var country: String? = null,
    var poster: String? = null,
    var imdbRating: String? = null,
    var imdbVotes: String? = null,
    var type: String? = null
) : RealmObject() {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is FavoriteMovieRealm) return false

        return imdbId == other.imdbId &&
                title == other.title &&
                year == other.year &&
                released == other.released &&
                runtime == other.runtime &&
                genre == other.genre &&
                director == other.director &&
                actors == other.actors &&
                plot == other.plot &&
                country == other.country &&
                poster == other.poster &&
                imdbRating == other.imdbRating &&
                imdbVotes == other.imdbVotes &&
                type == other.type
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + imdbId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + released.hashCode()
        result = 31 * result + runtime.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + director.hashCode()
        result = 31 * result + actors.hashCode()
        result = 31 * result + plot.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + poster.hashCode()
        result = 31 * result + imdbRating.hashCode()
        result = 31 * result + imdbVotes.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
