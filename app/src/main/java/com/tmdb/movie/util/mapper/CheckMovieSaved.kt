package com.tmdb.movie.util.mapper

import com.tmdb.movie.ui.model.MovieItem

fun List<MovieItem>.checkLikes(idList: List<Int>): List<MovieItem> {
    forEach { movieItem ->
        if (movieItem.id in idList) movieItem.isSelect = true
    }
    return this
}