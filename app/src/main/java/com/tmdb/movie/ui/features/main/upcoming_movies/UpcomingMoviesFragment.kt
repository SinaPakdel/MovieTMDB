package com.tmdb.movie.ui.features.main.upcoming_movies

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
import com.tmdb.movie.databinding.FragmentUpcomingMoviesBinding
import com.tmdb.movie.ui.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingMoviesFragment : Fragment(R.layout.fragment_upcoming_movies) {
    private var _binding: FragmentUpcomingMoviesBinding? = null

    private val binding get() = _binding!!
    private val upcomingMoviesViewModel: UpcomingMoviesViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingMoviesBinding.bind(view)
        movieAdapter = MovieAdapter(onclick = {}, onLikeStateClick = {})
        with(binding) {
            rvUpcomingMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }

        upcomingMoviesViewModel.upcomingMovies.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}