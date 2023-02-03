package com.adesso.movee.scene.moviedetail

import androidx.lifecycle.viewModelScope
import com.adesso.movee.R
import com.adesso.movee.base.BaseViewModel
import com.adesso.movee.domain.FetchMovieDetailUseCase
import com.adesso.movee.internal.popup.PopupListener
import com.adesso.movee.internal.popup.PopupModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val fetchMovieDetailUseCase: FetchMovieDetailUseCase
) : BaseViewModel() {

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

    override fun handleFailure(failure: Failure) {
        if (failure is Failure.NoConnectivityError) {
            navigate(
                PopupModel(
                    message = getString(R.string.common_error_network_connection)
                ),
                listener = PopupListener(
                    onPositiveButtonClick = { navigateBack() }
                )
            )
        } else {
            super.handleFailure(failure)
        }
    }
}
