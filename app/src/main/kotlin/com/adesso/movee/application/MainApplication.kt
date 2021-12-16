package com.adesso.movee.application

import android.app.Application
import com.adesso.movee.BuildConfig
import com.adesso.movee.internal.util.NetworkStateHolder.registerConnectivityMonitor
import com.adesso.movee.internal.util.TimberTree
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
