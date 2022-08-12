package com.papara.merchant.application

import android.app.Application
import com.papara.merchant.BuildConfig
import com.papara.merchant.internal.util.NetworkStateHolder.registerConnectivityMonitor
import com.papara.merchant.internal.util.TimberTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        registerConnectivityMonitor()
    }

    private fun initTimber() {
        if (BuildConfig.ENABLE_LOG) {
            Timber.plant(TimberTree.debug)
        } else {
            Timber.plant()
        }
    }
}
