package com.adesso.movee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM : BaseAndroidViewModel, B : ViewDataBinding> :
    DaggerFragment() {
    protected lateinit var decorator: BaseFragmentDecorator<VM, B>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected val binder get() = decorator.binder
    protected val viewModel get() = decorator.viewModel

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun initialize()

    protected inline fun <reified VM : ViewModel> activityViewModels() =
        decorator.activityViewModels<VM>()

    protected inline fun <reified VM : ViewModel> viewModels() =
        decorator.viewModels<VM>()

    protected inline fun <reified VM : ViewModel> parentViewModels() =
        decorator.parentViewModels<VM>()

    protected inline fun <reified VM : ViewModel> navGraphViewModels(@IdRes navGraphId: Int) =
        decorator.navGraphViewModels<VM>(navGraphId)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binder: B = DataBindingUtil.inflate(inflater, layoutId, container, false)
        decorator = BaseFragmentDecorator(this, binder, viewModelFactory)

        initialize()

        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        decorator.startObservers()
    }
}
