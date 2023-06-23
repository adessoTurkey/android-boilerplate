package com.adesso.movee.base

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.navigation
import androidx.navigation.fragment.findNavController
import com.adesso.movee.internal.extension.showPopup
import com.adesso.movee.navigation.NavigationCommand
import com.adesso.movee.scene.main.MainActivity

interface Navigation {

    fun Fragment.observeNavigation(viewModel: ViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { command ->
                handleNavigation(command)
            }
        }
    }

    private fun Fragment.handleNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ToDirection -> {
                findNavController().navigate(command.directions)
            }

            is NavigationCommand.ToDeepLink -> {
                (activity as? MainActivity)
                    ?.navController
                    ?.navigate(command.deepLink.toUri(), null)
            }

            is NavigationCommand.Popup -> {
                with(command) {
                    context?.showPopup(model, listener)
                }
            }

            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }
}
