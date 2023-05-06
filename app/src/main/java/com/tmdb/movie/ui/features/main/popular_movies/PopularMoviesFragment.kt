package com.tmdb.movie.ui.features.main.popular_movies

import android.os.Bundle
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
import com.tmdb.movie.databinding.FragmentPopularMoviesBinding
import com.tmdb.movie.ui.adapter.MovieAdapter
import com.tmdb.movie.ui.features.main.popular_movies.events.PopularEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies) {

    private val popularMoviesViewModel: PopularMoviesViewModel by viewModels()

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!
    private val navController: NavController by lazy { findNavController() }
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularMoviesBinding.bind(view)
        // TODO: implement id for onItemMovieSelected & onLikeStateClicked later !!!
        movieAdapter =
            MovieAdapter(
                onclick = { popularMoviesViewModel.onItemMovieSelected(1) },
                onLikeStateClick = {
                    popularMoviesViewModel.onLikeStateClicked(it)
                },
                onLongClickListener = {
                        popularMoviesViewModel.onLongItemMovieSelected(it)
                })

        popularMoviesViewModel.popularMovies.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }

        with(binding) {
            rvPopularMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }

        eventHandler()
    }

    private fun eventHandler() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                popularMoviesViewModel.popularEvent.collect { event ->
                    when (event) {
                        is PopularEvent.NavigateToDetailsScreen -> navController.navigate(
                            PopularMoviesFragmentDirections.actionGlobalMovieFragment(event.id)
                        )

                        is PopularEvent.LikeStateClicked -> {
                            // TODO: impl logic for handling likeState
                        }

                        is PopularEvent.LongItemMovieSelected -> {
                            // TODO: impl logic for longClickState like show fragmentDialog
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

}