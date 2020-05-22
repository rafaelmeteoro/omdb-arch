package com.meteoro.omdbarch.actions

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val EXTRA_IMDB = "com.meteoro.omdbarch.details.extra.imdb"

@Parcelize
data class ImdbArgs(val imdbId: String) : Parcelable
