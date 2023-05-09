package com.tmdb.movie.ui.features.main.popular_movies

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
import com.tmdb.movie.databinding.FragmentPopularMoviesBinding
import com.tmdb.movie.ui.adapter.EndlessRecyclerOnScrollListener
import com.tmdb.movie.ui.adapter.MovieAdapter
import com.tmdb.movie.ui.features.main.popular_movies.events.PopularEvent
import com.tmdb.movie.util.enums.StateHolder
import com.tmdb.movie.util.view.makeSnack
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
                onclick = { movieId ->
                    popularMoviesViewModel.onItemMovieSelected(movieId)
                },
                onLikeClicked = { movieItem ->
                    popularMoviesViewModel.onLikeStateClicked(movieItem)
                },
                onLongClickedListener = { movieItem ->
                    popularMoviesViewModel.onLongItemMovieSelected(movieItem)
                })
        with(binding) {
            rvPopularMovie.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }
        setPaging()
        eventHandler()
        checkState()
        popularMoviesViewModel.popularMovies.observe(viewLifecycleOwner) {
            Log.e("LMNOP", "onViewCreated: ${it.size}")
            movieAdapter.submitList(it)
        }
    }

    private fun eventHandler() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                popularMoviesViewModel.popularEvent.collect { event ->
                    when (event) {
                        is PopularEvent.NavigateToDetailsScreen -> navController.navigate(
                            PopularMoviesFragmentDirections.actionGlobalItemMovieBtmSheetDialog(event.id)
                        )

                        is PopularEvent.LikeStateClicked -> {
                            makeSnack(getString(R.string.item_successfully_added), binding.root)
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

    private fun setPaging() {
        binding.rvPopularMovie.addOnScrollListener(object : EndlessRecyclerOnScrollListener(
            binding.rvPopularMovie.layoutManager as GridLayoutManager,
            20
        ) {
            override fun onLoadMore(currentPage: Int) {
                popularMoviesViewModel.nextPage()
            }

        })
    }

    private fun checkState() {
        popularMoviesViewModel.stateHolder.observe(viewLifecycleOwner) { state ->
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
                    binding.rvPopularMovie.visibility = View.INVISIBLE
                }
            }
        }
    }
}