package com.tmdb.movie.ui.selected_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tmdb.movie.databinding.FragmentSelectedMoviesBinding

class SelectedMoviesFragment : Fragment() {

    private var _binding: FragmentSelectedMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val selectedMoviesViewModel =
            ViewModelProvider(this).get(SelectedMoviesViewModel::class.java)

        _binding = FragmentSelectedMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        selectedMoviesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}