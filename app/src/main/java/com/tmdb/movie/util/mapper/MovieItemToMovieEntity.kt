package com.tmdb.movie.util.mapper

import com.tmdb.movie.data.local.model.MovieEntity
import com.tmdb.movie.ui.model.MovieItem

fun MovieItem.asMovieEntity() = MovieEntity(title, posterPath, id)