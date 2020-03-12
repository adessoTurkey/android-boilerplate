package com.adesso.movee.internal.injection

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import com.adesso.movee.internal.injection.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class DaggerApplication :
    Application(),
    HasActivityInjector,
    HasServiceInjector,
    HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector
    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = fragmentInjector
    override fun serviceInjector(): DispatchingAndroidInjector<Service> = serviceInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().create(this).inject(this)
    }
}
