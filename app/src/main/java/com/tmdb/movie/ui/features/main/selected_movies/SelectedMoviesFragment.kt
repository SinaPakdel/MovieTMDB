package com.tmdb.movie.ui.features.main.selected_movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tmdb.movie.R
import com.tmdb.movie.databinding.FragmentSelectedMoviesBinding
import com.tmdb.movie.ui.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedMoviesFragment : Fragment(R.layout.fragment_selected_movies) {

    private var _binding: FragmentSelectedMoviesBinding? = null
    private val binding get() = _binding!!
    private val selectedMoviesViewModel: SelectedMoviesViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectedMoviesBinding.bind(view)
        movieAdapter = MovieAdapter(onclick = {}, onLikeStateClick = {})

        with(binding) {
            rvSelectedMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }

        selectedMoviesViewModel.selectedMovies.observe(viewLifecycleOwner){
            movieAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}