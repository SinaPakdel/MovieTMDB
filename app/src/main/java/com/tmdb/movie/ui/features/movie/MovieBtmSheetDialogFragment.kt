package com.tmdb.movie.ui.features.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tmdb.movie.R
import com.tmdb.movie.databinding.FragmentItemMovieBinding
import com.tmdb.movie.util.consts.Services
import com.tmdb.movie.util.enums.StateHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieBtmSheetDialogFragment : BottomSheetDialogFragment(R.layout.fragment_item_movie) {
private val TAG="FragmentMovieBtmSheetDialog"
    private var _binding: FragmentItemMovieBinding? = null
    private val binding get() = _binding!!
    private val movieBtmSheetDialogViewModel: MovieBtmSheetDialogViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG, "onCreateView: ", )
        _binding = FragmentItemMovieBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            movieBtmSheetDialogViewModel.movie.observe(viewLifecycleOwner) { movieDetailsItem ->
                Log.e(TAG, "onViewCreated: $movieDetailsItem", )
                tvTitleMovie.text = movieDetailsItem.originalTitle
                tvDescription.text = movieDetailsItem.overview
                tvVoteAverage.text = movieDetailsItem.voteAverage.toString()
                tvPopularity.text = movieDetailsItem.popularity.toString()
                Glide.with(this.root).load(Services.POSTER_BASE_URL + movieDetailsItem.backdropPath).into(imgCoverMovie)
                Glide.with(this.root).load(Services.POSTER_BASE_URL + movieDetailsItem.posterPath).into(imgPosterMovie)
            }
        }
        checkState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkState() {
        movieBtmSheetDialogViewModel.stateHolder.observe(viewLifecycleOwner) { state ->
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