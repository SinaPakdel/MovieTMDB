package com.tmdb.movie.ui.features.main.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PopularMoviesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is PopularMovies Fragment"
    }
    val text: LiveData<String> = _text
}