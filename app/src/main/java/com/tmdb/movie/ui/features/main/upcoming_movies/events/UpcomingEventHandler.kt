package com.tmdb.movie.ui.features.main.upcoming_movies.events

import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.ui.features.main.popular_movies.events.PopularEvent

sealed class UpcomingEventHandler {
    data class NavigateToDetailsScreen(val id: Int) : UpcomingEventHandler()
    data class LikeStateClicked(val movieItem: MovieItem) : UpcomingEventHandler()
    data class LongItemClicked(val movieItem: MovieItem) : UpcomingEventHandler()
}