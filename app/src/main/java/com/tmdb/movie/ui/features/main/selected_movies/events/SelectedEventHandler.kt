package com.tmdb.movie.ui.features.main.selected_movies.events

import com.tmdb.movie.model.ui.MovieItem

sealed class SelectedEventHandler() {
    data class ItemClicked(val movieId: Int) : SelectedEventHandler()
    data class LikeStateClicked(val movieItem: MovieItem) : SelectedEventHandler()
    data class LongClicked(val movieItem: MovieItem) : SelectedEventHandler()
    data class UndoAddedToSelectedClicked(val movieItem: MovieItem) : SelectedEventHandler()
}
