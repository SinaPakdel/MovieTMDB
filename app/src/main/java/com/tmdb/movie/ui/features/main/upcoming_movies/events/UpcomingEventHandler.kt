package com.tmdb.movie.ui.features.main.upcoming_movies.events

import com.tmdb.movie.ui.model.MovieItem

sealed class UpcomingEventHandler {
    data class NavigateToDetailsScreen(val id: Int) : UpcomingEventHandler()
    data class LikeStateClicked(val movieItem: MovieItem) : UpcomingEventHandler()
    data class UnlikeStateClicked(val movieItem: MovieItem) : UpcomingEventHandler()

    data class LongItemClicked(val movieItem: MovieItem) : UpcomingEventHandler()
}