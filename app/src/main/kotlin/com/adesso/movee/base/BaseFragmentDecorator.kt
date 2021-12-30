package com.adesso.movee.base

import androidx.core.net.toUri
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.adesso.movee.BR
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.extension.showPopup
import com.adesso.movee.internal.util.functional.lazyThreadSafetyNone
import com.adesso.movee.navigation.NavigationCommand
import com.adesso.movee.scene.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.ParameterizedType

class BaseFragmentDecorator<VM : BaseAndroidViewModel, B : ViewDataBinding>(
    val fragment: Fragment,
    val binder: B,
    val viewModelStoreOwner: ViewModelStoreOwner
) {
    @Suppress("UNCHECKED_CAST")
    val viewModel by lazyThreadSafetyNone {
        val persistentViewModelClass = (fragment.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>
        return@lazyThreadSafetyNone ViewModelProvider(viewModelStoreOwner).get(
            persistentViewModelClass
        )
    }

    init {
        with(binder) {
            lifecycleOwner = fragment.viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
        }
    }

    fun startObservers() {
        observeNavigation()
        observeFailure()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(fragment.viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { command ->
                handleNavigation(command)
            }
        }
        // fragment.viewLifecycleOwner.observeEvent(viewModel.navigation, ::handleNavigation)
        // viewModel.navigation.observeEvent(fragment.viewLifecycleOwner, ::handleNavigation)
    }

    private fun handleNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ToDirection -> {
                fragment.findNavController().navigate(command.directions, getExtras())
            }
            is NavigationCommand.ToDeepLink -> {
                (fragment.activity as? MainActivity)
                    ?.navController
                    ?.navigate(command.deepLink.toUri(), null, getExtras())
            }
            is NavigationCommand.Popup -> {
                with(command) {
                    fragment.context?.showPopup(model, listener)
                }
            }
            is NavigationCommand.Back -> fragment.findNavController().navigateUp()
        }
    }

    private fun observeFailure() {
        viewModel.failurePopup.observeNonNull(fragment.viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { popupUiModel ->
                fragment.context?.showPopup(popupUiModel)
            }
        }
    }

    fun showSnackBarMessage(message: String) {
        Snackbar.make(binder.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()
}
