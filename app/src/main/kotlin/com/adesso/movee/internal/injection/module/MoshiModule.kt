package com.adesso.movee.internal.injection.module

import com.adesso.movee.internal.util.DateAdapter
import com.adesso.movee.internal.util.ImageJsonAdapter
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MoshiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(ImageJsonAdapter())
            .add(DateAdapter())
            .add(Wrapped.ADAPTER_FACTORY)
            .build()
    }
}
