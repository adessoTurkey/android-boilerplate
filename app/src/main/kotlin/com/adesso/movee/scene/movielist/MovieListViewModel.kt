package com.adesso.movee.scene.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.navigate
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.handleFailure
import com.adesso.movee.base.navigate
import com.adesso.movee.domain.FetchNowPlayingMoviesUseCase
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.scene.movielist.model.MovieUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val fetchNowPlayingMoviesUseCase: FetchNowPlayingMoviesUseCase
) : ViewModel(), MovieItemListener {

    private val _nowPlayingMovies = MutableStateFlow<List<MovieUiModel>?>(null)
    val nowPlayingMovies: StateFlow<List<MovieUiModel>?>
        get() = _nowPlayingMovies.asStateFlow()

    init {
        fetchNowPlayingMovies()
    }

    internal fun fetchNowPlayingMovies() = viewModelScope.launch {
        fetchNowPlayingMoviesUseCase(UseCase.None) { result ->
            result.onSuccess { movieUiModels -> postNowPlayingMovies(movieUiModels) }
                .onFailure { failure -> handleFailure(failure) }
        }
    }

    private fun postNowPlayingMovies(movies: List<MovieUiModel>) {
        _nowPlayingMovies.value = movies
    }

    override fun onMovieItemClick(movie: MovieUiModel) {
        navigate(MovieListFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie.id))
    }
}
