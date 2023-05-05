package com.tmdb.movie.util.mapper

import com.tmdb.movie.model.dto.movies.MovieDto
import com.tmdb.movie.model.ui.MovieItem

fun List<MovieDto>?.asMoviesItem(): List<MovieItem> {
    return this?.map { movieDto ->
        movieDto.asMovieItem()
    } ?: emptyList()
}

private fun MovieDto.asMovieItem() = MovieItem(title, posterPath, id)