package com.tmdb.movie.util.mapper

import com.tmdb.movie.data.remote.model.movies.MovieDto
import com.tmdb.movie.ui.model.MovieItem

fun List<MovieDto>?.asMoviesItem(): List<MovieItem> {
    return this?.map { movieDto ->
        movieDto.asMovieItem()
    } ?: emptyList()
}

private fun MovieDto.asMovieItem() = MovieItem(title, posterPath, id)