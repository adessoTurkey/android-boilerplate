package com.adesso.movee.base

import androidx.annotation.IdRes
import androidx.core.net.toUri
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
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
    val viewModelFactory: ViewModelProvider.Factory
) {
    @Suppress("UNCHECKED_CAST")
    val viewModel by lazyThreadSafetyNone {
        val persistentViewModelClass = (fragment.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>
        return@lazyThreadSafetyNone ViewModelProvider(fragment, viewModelFactory)
            .get(persistentViewModelClass)
    }

    inline fun <reified VM : ViewModel> activityViewModels(): Lazy<VM> {
        return fragment.activityViewModels { viewModelFactory }
    }

    inline fun <reified VM : ViewModel> viewModels(): Lazy<VM> {
        return fragment.viewModels { viewModelFactory }
    }

    inline fun <reified VM : ViewModel> parentViewModels(): Lazy<VM> {
        return fragment.requireParentFragment().viewModels { viewModelFactory }
    }

    inline fun <reified VM : ViewModel> navGraphViewModels(@IdRes navGraphId: Int):
        Lazy<VM> {
            return fragment.navGraphViewModels(navGraphId) { viewModelFactory }
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
