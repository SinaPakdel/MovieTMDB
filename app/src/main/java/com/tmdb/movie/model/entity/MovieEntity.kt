package com.tmdb.movie.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entity")
data class MovieEntity(
    @ColumnInfo("movie_title") val title: String?,
    @ColumnInfo("movie_poster") val posterPath: String?,
    @PrimaryKey(autoGenerate = true) @ColumnInfo("movie_id") val id: Int?,
)