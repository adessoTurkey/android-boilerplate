package com.adesso.movee.internal.injection.module

import android.app.Application
import android.content.Context
import com.adesso.movee.internal.injection.DaggerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [MoshiModule::class])
internal class AppModule {

    @Provides
    @Singleton
    internal fun provideApplicationContext(application: DaggerApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideApplication(application: DaggerApplication): Application {
        return application
    }
}
