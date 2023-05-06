package com.tmdb.movie.ui.features.main.upcoming_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tmdb.movie.databinding.FragmentUpcomingMoviesBinding

class UpcomingMoviesFragment : Fragment() {

    private var _binding: FragmentUpcomingMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val upcomingMoviesViewModel =
            ViewModelProvider(this).get(UpcomingMoviesViewModel::class.java)

        _binding = FragmentUpcomingMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}