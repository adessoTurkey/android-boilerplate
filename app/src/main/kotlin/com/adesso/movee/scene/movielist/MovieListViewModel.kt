package com.adesso.movee.scene.movielist

import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseViewModel
import com.adesso.movee.domain.FetchNowPlayingMoviesUseCase
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.scene.movielist.model.MovieUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val fetchNowPlayingMoviesUseCase: FetchNowPlayingMoviesUseCase
) : BaseViewModel(), MovieItemListener {

    private val _nowPlayingMovies = MutableStateFlow<List<MovieUiModel>?>(null)
    val nowPlayingMovies: StateFlow<List<MovieUiModel>?>
        get() = _nowPlayingMovies.asStateFlow()

    init {
        fetchNowPlayingMovies()
    }

    internal fun fetchNowPlayingMovies() = viewModelScope.launch {
        fetchNowPlayingMoviesUseCase
            .run(UseCase.None)
            .onSuccess { postNowPlayingMovies(it) }
            .onFailure { handleFailure(it) }
    }

    private fun postNowPlayingMovies(movies: List<MovieUiModel>) {
        _nowPlayingMovies.value = movies
    }

    override fun onMovieItemClick(movie: MovieUiModel) {
        navigate(MovieListFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie.id))
    }
}
