package com.adesso.movee.scene.movie

import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentMovieBinding

class MovieFragment :
    BaseFragment<MovieViewModel, FragmentMovieBinding>() {

    override val layoutId: Int get() = R.layout.fragment_movie

    override fun initialize() {
        super.initialize()

        binder.popularMovieAdapter = PopularMovieListAdapter()
    }
}
