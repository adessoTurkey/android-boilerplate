package com.adesso.movee.scene.moviedetail

import androidx.lifecycle.viewModelScope
import com.adesso.movee.R
import com.adesso.movee.base.BaseViewModel
import com.adesso.movee.domain.FetchMovieDetailFlowUseCase
import com.adesso.movee.internal.extension.doOnFailed
import com.adesso.movee.internal.popup.PopupListener
import com.adesso.movee.internal.popup.PopupModel
import com.adesso.movee.internal.util.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val fetchMovieDetailFlowUseCase: FetchMovieDetailFlowUseCase
) : BaseViewModel() {

    fun fetchMovieDetail(id: Long) = flow {
        emitAll(
            fetchMovieDetailFlowUseCase.execute(FetchMovieDetailFlowUseCase.Params(id))
                .doOnFailed(::handleFailure)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

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
