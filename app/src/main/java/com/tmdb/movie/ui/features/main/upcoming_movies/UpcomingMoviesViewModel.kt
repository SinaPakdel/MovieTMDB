package com.tmdb.movie.ui.features.main.upcoming_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpcomingMoviesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is FragmentUpcomingMovies Fragment"
    }
    val text: LiveData<String> = _text
}