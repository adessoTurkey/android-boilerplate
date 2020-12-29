package com.adesso.movee.scene.movielist

import com.adesso.movee.R
import com.adesso.movee.base.BaseListAdapter
import com.adesso.movee.databinding.ItemMovieBinding
import com.adesso.movee.internal.extension.executeAfter
import com.adesso.movee.scene.movielist.model.MovieUiModel

interface MovieItemListener {

    fun onMovieItemClick(movie: MovieUiModel)
}

class MovieListAdapter(private val movieItemListener: MovieItemListener) :
    BaseListAdapter<ItemMovieBinding, MovieUiModel>() {

    override val layoutRes: Int get() = R.layout.item_movie

    override fun bind(binding: ItemMovieBinding, item: MovieUiModel) {
        binding.executeAfter {
            movie = item
            listener = movieItemListener
        }
    }
}
