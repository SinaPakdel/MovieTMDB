package com.tmdb.movie.util.mapper

import com.tmdb.movie.data.remote.model.details.Genre
import com.tmdb.movie.data.remote.model.details.MovieDetailsResponse
import com.tmdb.movie.ui.model.GenreItem
import com.tmdb.movie.ui.model.MovieDetailsItem

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
        backdropPath,
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