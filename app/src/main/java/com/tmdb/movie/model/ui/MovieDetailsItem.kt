package com.tmdb.movie.model.ui

import com.tmdb.movie.model.dto.Genre


data class MovieDetailsItem(
    val genres: List<Genre>?,
    val id: Int?,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val status: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)