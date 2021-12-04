package com.adesso.movee.application

import android.app.Activity
import android.os.Bundle
import com.adesso.movee.BuildConfig
import com.adesso.movee.internal.injection.DaggerApplication
import com.adesso.movee.internal.util.NetworkStateHolder.registerConnectivityMonitor
import com.adesso.movee.internal.util.TimberTree
import timber.log.Timber

class MainApplication : DaggerApplication() {

    lateinit var currentActivity: Activity

    override fun onCreate() {
        super.onCreate()
        initTimber()
        registerConnectivityMonitor()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivity = activity
            }

            override fun onActivityStarted(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    private fun initTimber() {
        if (BuildConfig.ENABLE_LOG) {
            Timber.plant(TimberTree.debug)
        } else {
            Timber.plant()
        }
    }
}
