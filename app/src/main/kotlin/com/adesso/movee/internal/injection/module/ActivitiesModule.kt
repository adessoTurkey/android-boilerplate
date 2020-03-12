package com.adesso.movee.internal.injection.module

import com.adesso.movee.internal.injection.scope.MainScope
import com.adesso.movee.scene.main.MainActivity
import com.adesso.movee.scene.main.MainModule
import com.adesso.movee.scene.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    internal abstract fun contributeSplashActivity(): SplashActivity
}
