package com.tmdb.movie.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tmdb.movie.databinding.ItemMovieBinding
import com.tmdb.movie.ui.model.MovieItem

class MovieAdapter(
    private val onclick: (Int) -> Unit,
    private val onLikeClicked: (MovieItem) -> Unit,
    private val onLongClickedListener: (MovieItem) -> Unit,
) :
    ListAdapter<MovieItem, MovieAdapter.ViewHolder>(object : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
            oldItem == newItem && newItem.isSelect == oldItem.isSelect
    }) {
    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener { getItem(adapterPosition).id?.let { id -> onclick(id) } }
                setOnLongClickListener {
                    onLongClickedListener(getItem(adapterPosition))
                    true
                }
            }
        }

        fun bind(item: MovieItem) {
            with(binding) {
                tvItemTitle.text = item.title
                imgLike.isInvisible = !item.isSelect
                Glide.with(binding.root).load(item.posterPath).into(itemMoviePoster)
                imgLike.apply {
                    setOnClickListener {
                        item.isSelect = true
                        visibility = View.INVISIBLE
                        onLikeClicked(getItem(adapterPosition))
                    }
                }
                imgUnlike.apply {
                    setOnClickListener {
                        item.isSelect = false
                        imgLike.visibility = View.VISIBLE
                        onLikeClicked(getItem(adapterPosition))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}