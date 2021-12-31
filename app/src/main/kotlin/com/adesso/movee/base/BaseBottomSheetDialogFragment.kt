package com.adesso.movee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<VM : BaseAndroidViewModel, B : ViewDataBinding> :
    BottomSheetDialogFragment() {

    protected lateinit var decorator: BaseFragmentDecorator<VM, B>

    protected val binder get() = decorator.binder
    protected val viewModel get() = decorator.viewModel

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun initialize()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        decorator.startObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binder: B = DataBindingUtil.inflate(inflater, layoutId, container, false)
        decorator = BaseFragmentDecorator(this, binder, this)

        initialize()
        return binder.root
    }
}
