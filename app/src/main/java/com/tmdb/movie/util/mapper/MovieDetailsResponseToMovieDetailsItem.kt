package com.tmdb.movie.util.mapper

import com.tmdb.movie.model.dto.details.Genre
import com.tmdb.movie.model.dto.details.MovieDetailsResponse
import com.tmdb.movie.model.ui.GenreItem
import com.tmdb.movie.model.ui.MovieDetailsItem

fun MovieDetailsResponse.asMovieDetailsItem(): MovieDetailsItem {
    return MovieDetailsItem(
        genresToGenresItem(genres),
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        runtime,
        status,
        voteAverage,
        voteCount
    )
}

private fun genresToGenresItem(genres: List<Genre>?): List<GenreItem> {
    return genres?.map { genre ->
        genre.asGenreItem()
    } ?: emptyList()
}

private fun Genre.asGenreItem() = GenreItem(id, name)