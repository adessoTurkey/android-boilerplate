package com.adesso.movee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.adesso.movee.BR
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.extension.showPopup
import com.adesso.movee.internal.util.functional.lazyThreadSafetyNone
import com.adesso.movee.navigation.NavigationCommand
import com.adesso.movee.scene.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.ParameterizedType

abstract class BaseBottomSheetDialogFragment<VM : BaseViewModel, B : ViewDataBinding> :
    BottomSheetDialogFragment() {

    protected lateinit var binder: B

    @get:LayoutRes
    abstract val layoutId: Int

    @Suppress("UNCHECKED_CAST")
    val viewModel by lazyThreadSafetyNone {
        val persistentViewModelClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>
        return@lazyThreadSafetyNone ViewModelProvider(this)[persistentViewModelClass]
    }

    open fun initialize() {
        // Do nothing in here. Child classes should implement when necessary
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binder.lifecycleOwner = viewLifecycleOwner
        binder.setVariable(BR.viewModel, viewModel)

        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNavigation()
        observeFailure()

        initialize()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { command ->
                handleNavigation(command)
            }
        }
    }

    protected open fun handleNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ToDirection -> {
                findNavController().navigate(command.directions, getExtras())
            }
            is NavigationCommand.ToDeepLink -> {
                (activity as? MainActivity)
                    ?.navController
                    ?.navigate(command.deepLink.toUri(), null, getExtras())
            }
            is NavigationCommand.Popup -> {
                with(command) {
                    context?.showPopup(model, listener)
                }
            }
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

    private fun observeFailure() {
        viewModel.failurePopup.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { popupUiModel ->
                requireContext().showPopup(popupUiModel)
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binder.root, message, Snackbar.LENGTH_LONG).show()
    }

    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()
}
