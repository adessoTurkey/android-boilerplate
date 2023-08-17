package com.adesso.movee.scene.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adesso.movee.R
import com.adesso.movee.base.FailureHandler
import com.adesso.movee.base.Navigation
import com.adesso.movee.base.viewBinding
import com.adesso.movee.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list), Navigation, FailureHandler {

    private val viewModel: MovieListViewModel by viewModels()
    private val binding by viewBinding(FragmentMovieListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeNavigation(viewModel)
        observeFailure(viewModel)
        binding.viewModel = viewModel
        binding.recyclerViewPopularMovies.adapter = MovieListAdapter(viewModel)
    }
}
