package com.tmdb.movie.ui.adapter

import android.view.LayoutInflater
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tmdb.movie.databinding.ItemMovieBinding
import com.tmdb.movie.model.ui.MovieItem

class MovieAdapter(
    private val onclick: (Int) -> Unit,
    private val onLikeStateClick: (MovieItem) -> Unit,
    private val onLongClickListener: (MovieItem) -> Unit,
) :
    ListAdapter<MovieItem, MovieAdapter.ViewHolder>(DiffCallback()) {
    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener { getItem(adapterPosition).id?.let { id -> onclick(id) } }
                setOnLongClickListener {
                    onLongClickListener(getItem(adapterPosition))
                    true
                }
            }
        }

        fun bind(item: MovieItem) {
            with(binding) {
                tvItemTitle.text = item.title
                Glide.with(binding.root).load(item.posterPath).into(itemMoviePoster)
                imgLike.apply {
                    setOnClickListener { onLikeStateClick(getItem(adapterPosition)) }
                }
            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}