package com.adesso.movee.application

import com.adesso.movee.BuildConfig
import com.adesso.movee.internal.injection.DaggerApplication
import com.adesso.movee.internal.util.TimberTree
import timber.log.Timber

class MainApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.ENABLE_LOG) {
            Timber.plant(TimberTree.debug)
        } else {
            Timber.plant()
        }
    }
}
