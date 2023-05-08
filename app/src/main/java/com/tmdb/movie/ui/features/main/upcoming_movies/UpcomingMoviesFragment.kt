package com.tmdb.movie.ui.features.main.upcoming_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tmdb.movie.R
import com.tmdb.movie.databinding.FragmentUpcomingMoviesBinding
import com.tmdb.movie.ui.adapter.MovieAdapter
import com.tmdb.movie.ui.features.main.upcoming_movies.events.UpcomingEventHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingMoviesFragment : Fragment(R.layout.fragment_upcoming_movies) {
    private val TAG = "UpcomingMoviesFragment"
    private var _binding: FragmentUpcomingMoviesBinding? = null
    private val navController: NavController by lazy { findNavController() }

    private val binding get() = _binding!!
    private val upcomingMoviesViewModel: UpcomingMoviesViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingMoviesBinding.bind(view)
        movieAdapter = MovieAdapter(onclick = { movieItem -> upcomingMoviesViewModel.navigateToDetailsScreen(movieItem) },
            onLikeStateClick = {},
            onLongClickListener = {})
        with(binding) {
            rvUpcomingMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }

        observers()
        eventHandler()
    }

    private fun observers() {
        upcomingMoviesViewModel.upcomingMovies.observe(viewLifecycleOwner) {
            Log.e(TAG, "onViewCreated: $it")
            movieAdapter.submitList(it)
        }
    }

    private fun eventHandler() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                upcomingMoviesViewModel.upcomingEventHandler.collect { event ->
                    when (event) {
                        is UpcomingEventHandler.NavigateToDetailsScreen -> navController.navigate(
                            UpcomingMoviesFragmentDirections.actionGlobalMovieFragment(
                                event.id
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}