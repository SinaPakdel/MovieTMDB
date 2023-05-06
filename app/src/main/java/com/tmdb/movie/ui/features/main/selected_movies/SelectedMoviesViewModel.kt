package com.tmdb.movie.ui.features.main.selected_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movie.data.repository.Repository
import com.tmdb.movie.model.ui.MovieItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectedMoviesViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _selectedMovies = MutableLiveData<List<MovieItem>>()
    val selectedMovies: LiveData<List<MovieItem>> = _selectedMovies
    private var job: Job? = null


    init {
        getSelectedMovies()
    }

    fun deleteMovie(movieItem: MovieItem) {
        viewModelScope.launch {
            repository.deleteMovie(movieItem)
        }
    }

    fun getSelectedMovies() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getSeSelectedMovies().collect { movieItem ->
                _selectedMovies.postValue(movieItem)
            }
        }
    }
}