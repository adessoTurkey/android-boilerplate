package com.adesso.movee.scene.moviedetail

import androidx.navigation.fragment.navArgs
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<MovieDetailViewModel, FragmentMovieDetailBinding>() {

    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun getViewModelClass() = MovieDetailViewModel::class.java

    override val layoutId = R.layout.fragment_movie_detail

    override fun initialize() {
        viewModel.fetchMovieDetail(args.id)
    }
}
