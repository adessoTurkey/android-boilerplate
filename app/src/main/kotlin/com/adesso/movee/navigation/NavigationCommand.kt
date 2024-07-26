package com.adesso.movee.navigation

import androidx.navigation.NavDirections
import com.adesso.movee.internal.popup.PopupListener
import com.adesso.movee.internal.popup.PopupModel

/**
 * A simple sealed class to handle navigation properly from a [androidx.lifecycle.ViewModel]
 */
sealed class NavigationCommand {
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    data class ToDeepLink(val deepLink: String) : NavigationCommand()
    data class Popup(val model: PopupModel, val listener: PopupListener? = null) :
        NavigationCommand()

    object Back : NavigationCommand()
}
