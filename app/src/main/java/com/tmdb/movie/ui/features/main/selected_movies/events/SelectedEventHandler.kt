package com.tmdb.movie.ui.features.main.selected_movies.events

import com.tmdb.movie.ui.model.MovieItem

sealed class SelectedEventHandler() {
    data class ItemClicked(val movieId: Int) : SelectedEventHandler()
    data class LikeStateClicked(val movieItem: MovieItem) : SelectedEventHandler()
    data class LongClicked(val movieItem: MovieItem) : SelectedEventHandler()
}
