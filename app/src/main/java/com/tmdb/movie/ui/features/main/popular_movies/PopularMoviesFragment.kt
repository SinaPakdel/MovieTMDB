package com.tmdb.movie.ui.features.main.popular_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tmdb.movie.R
import com.tmdb.movie.databinding.FragmentPopularMoviesBinding
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.ui.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies) {

    private val popularMoviesViewModel: PopularMoviesViewModel by viewModels()

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentPopularMoviesBinding.bind(view)
        movieAdapter = MovieAdapter(onclick = {}, onLikeStateClick = {})

        popularMoviesViewModel.popularMovies.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }
        with(binding) {
            rvPopularMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}