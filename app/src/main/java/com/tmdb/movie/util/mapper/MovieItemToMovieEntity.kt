package com.tmdb.movie.util.mapper

import com.tmdb.movie.model.entity.MovieEntity
import com.tmdb.movie.model.ui.MovieItem

fun MovieItem.asMovieEntity() = MovieEntity(title, posterPath, id)