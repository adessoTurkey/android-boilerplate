package com.papara.merchant.base

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.papara.merchant.R
import com.papara.merchant.internal.popup.PopupListener
import com.papara.merchant.internal.popup.PopupModel
import com.papara.merchant.internal.util.Event
import com.papara.merchant.internal.util.Failure
import com.papara.merchant.internal.util.ResourceProvider
import com.papara.merchant.navigation.NavigationCommand
import javax.inject.Inject

@Suppress("ConvertSecondaryConstructorToPrimary")
@SuppressLint("StaticFieldLeak")
abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val _failurePopup = MutableLiveData<Event<PopupModel>>()
    val failurePopup: LiveData<Event<PopupModel>> get() = _failurePopup

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    protected open fun handleFailure(failure: Failure) {
        val message = when (failure) {
            is Failure.NoConnectivityError ->
                getString(R.string.common_error_network_connection)
            is Failure.ApiError ->
                failure.message
            is Failure.UnknownError ->
                failure.exception.localizedMessage
                    ?: getString(R.string.common_error_unknown)
            is Failure.TimeOutError ->
                getString(R.string.common_error_timeout)
            else ->
                failure.message ?: failure.toString()
        }

        _failurePopup.value = Event(
            PopupModel(
                message = message
            )
        )
    }

    fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToDirection(directions))
    }

    fun navigate(deepLink: String) {
        _navigation.value = Event(NavigationCommand.ToDeepLink(deepLink))
    }

    fun navigate(@StringRes deepLinkRes: Int) {
        navigate(getString(deepLinkRes))
    }

    fun navigate(model: PopupModel, listener: PopupListener?) {
        _navigation.value = Event(NavigationCommand.Popup(model, listener))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

    protected fun getString(@StringRes resId: Int): String {
        return resourceProvider.getString(resId)
    }
}
