package com.adesso.movee.scene.moviedetail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentMovieDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchMovieDetail(args.id).collect {
                    binder.textViewOverview.text = it?.overview
                    binder.layoutMovieDetail.movieDetail = it
                }
            }
        }
    }
}
