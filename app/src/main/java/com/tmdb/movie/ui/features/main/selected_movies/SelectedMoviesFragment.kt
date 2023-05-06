package com.tmdb.movie.ui.features.main.selected_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tmdb.movie.databinding.FragmentSelectedMoviesBinding

class SelectedMoviesFragment : Fragment() {
    val selectedMoviesViewModel: SelectedMoviesViewModel by viewModels()

    private var _binding: FragmentSelectedMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}