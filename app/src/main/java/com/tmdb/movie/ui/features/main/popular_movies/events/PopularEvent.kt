package com.tmdb.movie.ui.features.main.popular_movies.events

import com.tmdb.movie.model.ui.MovieItem

sealed class PopularEvent {
    data class NavigateToDetailsScreen(val id: Int) : PopularEvent()
    data class LikeStateClicked(val movieItem: MovieItem) : PopularEvent()
    data class LongItemMovieSelected(val movieItem: MovieItem) : PopularEvent()
}
