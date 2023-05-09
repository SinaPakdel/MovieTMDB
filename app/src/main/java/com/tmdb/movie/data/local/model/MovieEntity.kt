package com.tmdb.movie.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @ColumnInfo("movie_title") val title: String?,
    @ColumnInfo("movie_poster") val posterPath: String?,
    @PrimaryKey(autoGenerate = true) @ColumnInfo("movie_id") val id: Int?,
)
