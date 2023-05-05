package com.tmdb.movie.util.mapper

import com.tmdb.movie.model.entity.MovieEntity
import com.tmdb.movie.model.ui.MovieItem

fun List<MovieEntity>.asMoviesItem() = this.map { movieEntity ->
    movieEntity.asMovieItem()
}

private fun MovieEntity.asMovieItem() = MovieItem(title, posterPath, id)