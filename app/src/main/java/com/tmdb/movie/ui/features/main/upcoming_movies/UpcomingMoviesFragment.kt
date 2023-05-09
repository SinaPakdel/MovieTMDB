package com.tmdb.movie.ui.features.main.upcoming_movies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tmdb.movie.R
import com.tmdb.movie.databinding.FragmentUpcomingMoviesBinding
import com.tmdb.movie.ui.adapter.MovieAdapter
import com.tmdb.movie.ui.features.main.upcoming_movies.events.UpcomingEventHandler
import com.tmdb.movie.util.enums.StateHolder
import com.tmdb.movie.util.view.makeSnack
import dagger.hilt.android.AndroidEntryPoint
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
        movieAdapter = MovieAdapter(onclick = { movieItem ->
            upcomingMoviesViewModel.navigateToDetailsScreen(movieItem)
        },
            onLikeClicked = { movieItem ->
                upcomingMoviesViewModel.onLikeOrUnLikeClicked(movieItem)
            },
            onLongClickedListener = { movieItem ->
                upcomingMoviesViewModel.onLongItemClicked(movieItem)
            })
        with(binding) {
            rvUpcomingMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }

        observers()
        eventHandler()
        checkState()
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

                        is UpcomingEventHandler.LikeStateClicked -> makeSnack(
                            getString(R.string.item_successfully_added),
                            binding.root
                        )

                        is UpcomingEventHandler.UnlikeStateClicked -> {
                            makeSnack(getString(R.string.item_successfully_deleted), binding.root)
                        }

                        is UpcomingEventHandler.LongItemClicked -> {
                            // TODO: impl  LongItemClicked later
                        }


                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkState() {
        upcomingMoviesViewModel.stateHolder.observe(viewLifecycleOwner) { state ->
            when (state) {
                StateHolder.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                StateHolder.SUCCESS -> {
                    binding.tvNetworkFailed.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }

                StateHolder.ERROR -> {
                    binding.tvNetworkFailed.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.rvUpcomingMovie.visibility = View.INVISIBLE
                }
            }
        }
    }
}