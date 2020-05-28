package org.example.mjworkspace.myapplication.fragments.m.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity(tableName = "now")
@JsonClass(generateAdapter = true)
data class Now(
    @PrimaryKey
    @Json(name="id")
    val id: Int,
    @Json(name="title")
    val title: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name="vote_average")
    val voteAverage: Float,
    @Json(name="popularity")
    val popularity: Float,
    @Json(name="poster_path")
    val posterPath: String?
)