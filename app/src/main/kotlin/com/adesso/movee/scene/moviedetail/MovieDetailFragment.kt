package com.adesso.movee.scene.moviedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.adesso.movee.R
import com.adesso.movee.base.FailureHandler
import com.adesso.movee.base.Navigation
import com.adesso.movee.base.viewBinding
import com.adesso.movee.databinding.FragmentMovieDetailBinding
import com.adesso.movee.internal.extension.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail), Navigation, FailureHandler {

    private val args by navArgs<MovieDetailFragmentArgs>()
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val binding by viewBinding(FragmentMovieDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeNavigation(viewModel)
        observeFailure(viewModel)

        viewModel.fetchMovieDetail(args.id)
        collectFlow(viewModel.movieDetailFlow) { movieDetail ->
            movieDetail?.let {
                binding.layoutMovieDetail.movieDetail = it
                binding.textViewOverview.text = it.overview
            }
        }
    }
}
