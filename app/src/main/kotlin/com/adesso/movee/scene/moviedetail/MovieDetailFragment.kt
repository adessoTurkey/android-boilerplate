package com.adesso.movee.scene.moviedetail

import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentMovieDetailBinding
import com.adesso.movee.internal.extension.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<MovieDetailViewModel, FragmentMovieDetailBinding>() {

    private val args by navArgs<MovieDetailFragmentArgs>()

    override val layoutId = R.layout.fragment_movie_detail

    override fun initialize() {
        initReceivers()
    }

    private fun initReceivers() {
        collectFlow(
            flow = viewModel.fetchMovieDetail(args.id),
            minActiveState = Lifecycle.State.STARTED
        ) {
            binder.textViewOverview.text = it?.overview
            binder.layoutMovieDetail.movieDetail = it
        }
    }
}
