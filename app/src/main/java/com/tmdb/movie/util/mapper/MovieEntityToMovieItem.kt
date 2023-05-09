package com.tmdb.movie.util.mapper

import com.tmdb.movie.data.local.model.MovieEntity
import com.tmdb.movie.ui.model.MovieItem

fun List<MovieEntity>.asMoviesItem() = this.map { movieEntity ->
    movieEntity.asMovieItem()
}

private fun MovieEntity.asMovieItem() = MovieItem(title, posterPath, id)