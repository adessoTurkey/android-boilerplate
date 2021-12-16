package com.adesso.movee.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adesso.movee.internal.util.functional.lazyThreadSafetyNone

abstract class BaseActivity<VM : BaseAndroidViewModel> : AppCompatActivity() {

    protected val viewModel by lazyThreadSafetyNone {
        return@lazyThreadSafetyNone ViewModelProvider(this)[getViewModelClass()]
    }

    abstract fun getViewModelClass(): Class<VM>
}
