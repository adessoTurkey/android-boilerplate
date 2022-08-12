package com.papara.merchant.navigation

import androidx.navigation.NavDirections
import com.papara.merchant.internal.popup.PopupListener
import com.papara.merchant.internal.popup.PopupModel

/**
 * A simple sealed class to handle more properly
 * navigation from a [com.papara.merchant.base.BaseViewModel]
 */
sealed class NavigationCommand {
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    data class ToDeepLink(val deepLink: String) : NavigationCommand()
    data class Popup(val model: PopupModel, val listener: PopupListener? = null) :
        NavigationCommand()

    object Back : NavigationCommand()
}
