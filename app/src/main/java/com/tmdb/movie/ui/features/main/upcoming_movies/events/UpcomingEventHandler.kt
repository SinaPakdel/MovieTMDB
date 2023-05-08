package com.tmdb.movie.ui.features.main.upcoming_movies.events

import com.tmdb.movie.ui.features.main.popular_movies.events.PopularEvent

sealed class UpcomingEventHandler {
    data class NavigateToDetailsScreen(val id: Int) : UpcomingEventHandler()
}