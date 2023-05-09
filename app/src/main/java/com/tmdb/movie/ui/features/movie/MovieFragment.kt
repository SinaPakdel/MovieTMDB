package com.tmdb.movie.ui.features.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tmdb.movie.R
import com.tmdb.movie.databinding.FragmentMovieBinding
import com.tmdb.movie.util.consts.Services.POSTER_BASE_URL
import com.tmdb.movie.util.enums.StateHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BottomSheetDialogFragment(R.layout.fragment_movie) {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieBinding.bind(view)
        with(binding) {
            movieViewModel.movie.observe(viewLifecycleOwner) { movieDetailsItem ->
                tvTitleMovie.text = movieDetailsItem.originalTitle
                tvDescription.text = movieDetailsItem.overview
                tvVoteAverage.text = movieDetailsItem.voteAverage.toString()
                tvPopularity.text = movieDetailsItem.popularity.toString()
                Glide.with(this.root).load(POSTER_BASE_URL + movieDetailsItem.backdropPath).into(imgCoverMovie)
                Glide.with(this.root).load(POSTER_BASE_URL + movieDetailsItem.posterPath).into(imgPosterMovie)
            }
        }
        checkState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkState() {
        movieViewModel.stateHolder.observe(viewLifecycleOwner) { state ->
            when (state) {
                StateHolder.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                StateHolder.SUCCESS -> {
                    binding.hostDetails.visibility = View.VISIBLE
                    binding.tvNetworkFailed.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }

                StateHolder.ERROR -> {
                    binding.tvNetworkFailed.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}