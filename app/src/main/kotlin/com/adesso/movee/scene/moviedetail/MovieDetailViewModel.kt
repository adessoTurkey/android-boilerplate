package com.adesso.movee.scene.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.handleFailure
import com.adesso.movee.domain.FetchMovieDetailUseCase
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val fetchMovieDetailUseCase: FetchMovieDetailUseCase
) : ViewModel() {

    private val _movieDetailFlow = MutableStateFlow<MovieDetailUiModel?>(null)
    val movieDetailFlow: StateFlow<MovieDetailUiModel?>
        get() = _movieDetailFlow.asStateFlow()

    fun fetchMovieDetail(id: Long) = fetchMovieDetailUseCase(
        FetchMovieDetailUseCase.Params(id),
        viewModelScope
    ) { result ->
        result.onSuccess { handleMovieDetails(it) }.onFailure { handleFailure(it) }
    }

    private fun handleMovieDetails(movieDetailUiModel: MovieDetailUiModel) {
        _movieDetailFlow.value = movieDetailUiModel
    }
}
