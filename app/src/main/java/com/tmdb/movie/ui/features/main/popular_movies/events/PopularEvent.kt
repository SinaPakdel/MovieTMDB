package com.tmdb.movie.ui.features.main.popular_movies.events

import com.tmdb.movie.ui.model.MovieItem

sealed class PopularEvent {
    data class NavigateToDetailsScreen(val id: Int) : PopularEvent()
    data class LikeStateClicked(val movieItem: MovieItem) : PopularEvent()

    data class UnlikeStateClicked(val movieItem: MovieItem) : PopularEvent()

    data class LongItemMovieSelected(val movieItem: MovieItem) : PopularEvent()
}
