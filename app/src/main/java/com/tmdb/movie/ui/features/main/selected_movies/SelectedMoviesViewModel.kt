package com.tmdb.movie.ui.features.main.selected_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectedMoviesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is FragmentSelectedMovies Fragment"
    }
    val text: LiveData<String> = _text
}