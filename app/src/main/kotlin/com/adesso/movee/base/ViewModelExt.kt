package com.adesso.movee.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.navigate
import androidx.lifecycle.setFailure
import androidx.navigation.NavDirections
import com.adesso.movee.R
import com.adesso.movee.internal.popup.PopupListener
import com.adesso.movee.internal.popup.PopupModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.navigation.NavigationCommand

fun ViewModel.navigate(directions: NavDirections) {
    navigate(NavigationCommand.ToDirection(directions))
}

fun ViewModel.navigate(deepLink: String) {
    navigate(NavigationCommand.ToDeepLink(deepLink))
}

fun ViewModel.navigate(model: PopupModel, listener: PopupListener?) {
    navigate(NavigationCommand.Popup(model, listener))
}

fun ViewModel.navigateBack() {
    navigate(NavigationCommand.Back)
}

fun ViewModel.handleFailure(failure: Failure, listener: PopupListener? = null) {
    this.setFailure(FailureData(failure.asPopupModel(), listener))
}

private fun Failure.asPopupModel(): PopupModel {
    return when (this) {
        is Failure.NoConnectivityError ->
            PopupModel(messageInt = R.string.common_error_network_connection)

        is Failure.NetworkError, is Failure.UnknownError ->
            if (message.isNullOrBlank()) {
                PopupModel(messageInt = R.string.common_error_unknown)
            } else {
                PopupModel(message = message)
            }

        is Failure.TimeOutError ->
            PopupModel(messageInt = R.string.common_error_timeout)

        else ->
            PopupModel(message = message ?: toString())
    }
}

data class FailureData(val popupModel: PopupModel, val listener: PopupListener? = null)
