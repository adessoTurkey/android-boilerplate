package com.adesso.movee.scene.movielist

import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentMovieListBinding

class MovieListFragment :
    BaseFragment<MovieListViewModel, FragmentMovieListBinding>() {

    override val layoutId: Int get() = R.layout.fragment_movie_list

    override fun initialize() {
        binder.movieAdapter = MovieListAdapter(viewModel)
    }
}
