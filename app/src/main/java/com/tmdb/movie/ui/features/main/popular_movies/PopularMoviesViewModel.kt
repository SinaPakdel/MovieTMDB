package com.tmdb.movie.ui.features.main.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movie.data.repository.Repository
import com.tmdb.movie.model.ui.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val popularMoviesList = arrayListOf<MovieItem>()
    private val _popularMovies = MutableLiveData<List<MovieItem>>()
    val popularMovies: LiveData<List<MovieItem>> = _popularMovies
    private var page = 1
    private var job: Job? = null

    init {
        getPopularMovies()
    }

    fun saveMovie(movieItem: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movieItem)
        }
    }

    fun getPopularMovies() {
        job?.cancel()
        job = viewModelScope.launch {
            addPopularMovies(repository.getPopularMovies(page))
            _popularMovies.postValue(popularMoviesList)
        }
    }

    fun nextPage() {
        page++
        getPopularMovies()
    }

    private fun addPopularMovies(popularMovies: List<MovieItem>) {
        popularMoviesList.addAll(popularMovies)
    }
}