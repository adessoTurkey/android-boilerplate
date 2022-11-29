package com.adesso.movee.internal.injection.module

import android.content.Context
import com.adesso.movee.BuildConfig
import com.adesso.movee.data.remote.api.MovieService
import com.adesso.movee.internal.util.api.ApiKeyInterceptor
import com.adesso.movee.internal.util.api.NetworkCallAdapter
import com.adesso.movee.internal.util.api.RetryAfterInterceptor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Logger
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    companion object {
        private const val CLIENT_TIME_OUT_SEC = 30L
    }

    @Provides
    @Singleton
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.ENABLE_LOG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Provides
    @Singleton
    internal fun provideCurlInterceptor(): CurlInterceptor {
        return CurlInterceptor(object : Logger {
            override fun log(message: String) {
                if (BuildConfig.ENABLE_LOG) println(message)
            }
        })
    }

    @Provides
    @Singleton
    internal fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).collector(ChuckerCollector(context)).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        curlInterceptor: CurlInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(curlInterceptor)
            .addInterceptor(RetryAfterInterceptor())

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: Lazy<OkHttpClient>, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(NetworkCallAdapter())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .callFactory { client.get().newCall(it) }
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}
