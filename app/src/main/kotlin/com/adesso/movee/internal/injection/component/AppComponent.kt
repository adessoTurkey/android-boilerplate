package com.adesso.movee.internal.injection.component

import com.adesso.movee.internal.injection.DaggerApplication
import com.adesso.movee.internal.injection.module.ActivitiesModule
import com.adesso.movee.internal.injection.module.AppModule
import com.adesso.movee.internal.injection.module.FragmentsModule
import com.adesso.movee.internal.injection.module.NetworkModule
import com.adesso.movee.internal.injection.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        FragmentsModule::class,
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
internal interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DaggerApplication>()
}
