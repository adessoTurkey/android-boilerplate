package com.adesso.movee.base

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.adesso.movee.internal.util.functional.lazyThreadSafetyNone
import dagger.android.support.DaggerAppCompatActivity
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<VM : BaseAndroidViewModel> : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Suppress("UNCHECKED_CAST")
    protected val viewModel by lazyThreadSafetyNone {
        val persistentViewModelClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>
        return@lazyThreadSafetyNone ViewModelProviders.of(this, viewModelFactory)
            .get(persistentViewModelClass)
    }
}
