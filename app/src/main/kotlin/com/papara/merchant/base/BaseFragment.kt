package com.papara.merchant.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.papara.merchant.BR
import com.papara.merchant.R
import com.papara.merchant.internal.extension.observeNonNull
import com.papara.merchant.internal.extension.showPopup
import com.papara.merchant.internal.util.functional.lazyThreadSafetyNone
import com.papara.merchant.navigation.NavigationCommand
import com.papara.merchant.scene.main.MainActivity
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment() {

    internal lateinit var binder: B

    @get:LayoutRes
    abstract val layoutId: Int

    private var snackbar: Snackbar? = null
    private var networkStateBar: Snackbar? = null

    open fun initialize() {
        // Do nothing in here. Child classes should implement when necessary
    }

    @Suppress("UNCHECKED_CAST")
    protected open val viewModel by lazyThreadSafetyNone {
        val persistentViewModelClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>
        return@lazyThreadSafetyNone ViewModelProvider(this)[persistentViewModelClass]
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
            it.getContentIfNotHandled()?.let { popupModel ->
                requireContext().showPopup(popupModel)
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        context?.let {
            try {
                snackbar?.dismiss()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            snackbar = Snackbar.make(
                binder.root,
                message,
                Snackbar.LENGTH_LONG
            )
            snackbar?.setTextColor(
                ContextCompat.getColor(it, R.color.colorAccent)
            )
            snackbar?.setBackgroundTint(
                ContextCompat.getColor(it, R.color.colorPrimary)
            )
            snackbar?.show()
        }
    }

    private fun showNetworkSnackBar() {
        initNetworkSnackBarIfNeeded()
        networkStateBar?.show()
    }

    private fun hideNetworkSnackBar() {
        networkStateBar?.dismiss()
    }

    private fun initNetworkSnackBarIfNeeded() {
        if (networkStateBar == null) {
            context?.let {
                networkStateBar = Snackbar.make(
                    binder.root,
                    R.string.common_error_network_connection,
                    Snackbar.LENGTH_INDEFINITE
                )
                networkStateBar?.setTextColor(
                    ContextCompat.getColor(it, R.color.colorAccent)
                )
                networkStateBar?.setBackgroundTint(
                    ContextCompat.getColor(it, R.color.colorPrimary)
                )
            }
        }
    }

    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()
}
