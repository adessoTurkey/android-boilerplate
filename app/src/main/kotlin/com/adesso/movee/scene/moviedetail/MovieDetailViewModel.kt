package com.adesso.movee.scene.moviedetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adesso.movee.R
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchMovieDetailUseCase
import com.adesso.movee.internal.popup.PopupListener
import com.adesso.movee.internal.popup.PopupModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.scene.moviedetail.model.MovieDetailUiModel
import javax.inject.Inject
import kotlinx.coroutines.launch

class MovieDetailViewModel @Inject constructor(
    application: Application,
    private val fetchMovieDetailUseCase: FetchMovieDetailUseCase
) : BaseAndroidViewModel(application) {
    private val _movieDetail = MutableLiveData<MovieDetailUiModel>()
    val movieDetail: LiveData<MovieDetailUiModel> get() = _movieDetail

    fun fetchMovieDetail(id: Long) = viewModelScope.launch {
        fetchMovieDetailUseCase
            .run(FetchMovieDetailUseCase.Params(id))
            .either(::handleFailure, ::postMovieDetail)
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
