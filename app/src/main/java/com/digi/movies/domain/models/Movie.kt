package com.digi.movies.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")

data class Movie(
    @PrimaryKey
    val id: Int?=null,
    val adult: Boolean?=null,
    val backdrop_path: String?=null,
    val genre_ids: List<Int>?=null,
    var genre_id: Int?=null,

    val media_type: String?=null,
    val original_language: String?=null,
    val original_title: String?=null,
    val overview: String?=null,
    val popularity: String?=null,
    val poster_path: String?=null,
    val release_date: String?=null,
    val title: String?=null,
    val video: Boolean?=null,
    val vote_average: String?=null,
    val vote_count: Int?=null
) : java.io.Serializable