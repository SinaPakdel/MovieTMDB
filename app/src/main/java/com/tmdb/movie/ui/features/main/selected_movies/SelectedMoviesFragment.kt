package com.tmdb.movie.ui.features.main.selected_movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tmdb.movie.R
import com.tmdb.movie.databinding.FragmentSelectedMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedMoviesFragment : Fragment(R.layout.fragment_selected_movies) {
    val selectedMoviesViewModel: SelectedMoviesViewModel by viewModels()

    private var _binding: FragmentSelectedMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentSelectedMoviesBinding.bind(view)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}