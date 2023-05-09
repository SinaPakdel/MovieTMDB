package com.tmdb.movie.ui.features.main.selected_movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tmdb.movie.R
import com.tmdb.movie.databinding.FragmentSelectedMoviesBinding
import com.tmdb.movie.ui.adapter.MovieAdapter
import com.tmdb.movie.ui.features.main.selected_movies.events.SelectedEventHandler
import com.tmdb.movie.util.view.makeSnackAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectedMoviesFragment : Fragment(R.layout.fragment_selected_movies) {

    private var _binding: FragmentSelectedMoviesBinding? = null
    private val binding get() = _binding!!
    private val selectedMoviesViewModel: SelectedMoviesViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private val navController: NavController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectedMoviesBinding.bind(view)
        movieAdapter = MovieAdapter(onclick = { movieId ->
            selectedMoviesViewModel.onItemClicked(movieId)
        }, onLikeClicked = { movieItem ->
            selectedMoviesViewModel.onLikeStateClicked(movieItem)

        }, onLongClickedListener = { movieItem ->
            selectedMoviesViewModel.onLongClicked(movieItem)
        })
        eventHandler()
        with(binding) {
            rvSelectedMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }

        selectedMoviesViewModel.selectedMovies.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }

    }

    private fun eventHandler() {
        viewLifecycleOwner.lifecycleScope.launch {
            selectedMoviesViewModel.selectedEventHandler.collect { event ->
                when (event) {
                    is SelectedEventHandler.ItemClicked -> navController.navigate(
                        SelectedMoviesFragmentDirections.actionGlobalMovieFragment(event.movieId)
                    )

                    is SelectedEventHandler.LikeStateClicked -> {
                        makeSnackAction(getString(R.string.item_successfully_added), "undo", binding.root) {
                            selectedMoviesViewModel.onUndoAddedToSelectedClicked(event.movieItem)
                        }
                    }

                    is SelectedEventHandler.LongClicked -> {
                        // TODO: impl longClickedState later!! 
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