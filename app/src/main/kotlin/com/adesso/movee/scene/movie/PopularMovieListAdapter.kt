package com.adesso.movee.scene.movie

import com.adesso.movee.R
import com.adesso.movee.base.BaseListAdapter
import com.adesso.movee.databinding.ItemPopularMovieBinding
import com.adesso.movee.internal.extension.executeAfter
import com.adesso.movee.uimodel.MovieUiModel

class PopularMovieListAdapter :
    BaseListAdapter<ItemPopularMovieBinding, MovieUiModel>() {

    override val layoutRes: Int get() = R.layout.item_popular_movie

    override fun bind(binding: ItemPopularMovieBinding, item: MovieUiModel) {
        binding.executeAfter {
            movie = item
        }
    }
}
