package com.adesso.movee.internal.injection.module

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides

@Module
object CacheModule {

    @Provides
    @JvmStatic
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(
            application.applicationContext
        )
    }
}
