package com.adesso.movee.scene.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adesso.movee.R
import com.adesso.movee.base.BaseViewModel
import com.adesso.movee.domain.FetchMovieDetailFlowUseCase
import com.adesso.movee.internal.popup.PopupListener
import com.adesso.movee.internal.popup.PopupModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val fetchMovieDetailFlowUseCase: FetchMovieDetailFlowUseCase
) : BaseViewModel() {

    private val _movieDetail = MutableLiveData<MovieDetailUiModel>()
    val movieDetail: LiveData<MovieDetailUiModel> get() = _movieDetail

    fun fetchMovieDetail(id: Long) = viewModelScope.launch {
        fetchMovieDetailFlowUseCase.execute(FetchMovieDetailFlowUseCase.Params(id))
            .catch { failure ->
                handleFailure(failure as Failure)
            }
            .collect { response ->
                postMovieDetail(response)
            }
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

    private fun postMovieDetail(movieDetailUiModel: MovieDetailUiModel) {
        _movieDetail.value = movieDetailUiModel
    }
}
