package com.adesso.movee.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.failurePopup
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.extension.showPopup

interface FailureHandler {

    fun Fragment.observeFailure(viewModel: ViewModel) {
        viewModel.failurePopup.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { failureData ->
                requireContext().showPopup(failureData.popupModel, failureData.listener)
            }
        }
    }
}
