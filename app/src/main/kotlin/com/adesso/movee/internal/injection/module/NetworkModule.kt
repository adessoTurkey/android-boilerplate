package com.adesso.movee.internal.injection.module

import android.content.Context
import com.adesso.movee.BuildConfig
import com.adesso.movee.data.remote.api.MovieService
import com.adesso.movee.internal.util.NetworkHandler
import com.adesso.movee.internal.util.api.ApiKeyInterceptor
import com.adesso.movee.internal.util.api.ErrorHandlingInterceptor
import com.adesso.movee.internal.util.api.RetryAfterInterceptor
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Loggable
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
internal class NetworkModule {

    companion object {
        private const val CLIENT_TIME_OUT = 120L
        private const val CLIENT_CACHE_SIZE = 10 * 1024 * 1024L
        private const val CLIENT_CACHE_DIRECTORY = "http"
    }

    /**
     * Create Cache object for OkHttp instance
     */
    @Provides
    @Singleton
    internal fun providesCache(context: Context): Cache = Cache(
        File(
            context.cacheDir,
            CLIENT_CACHE_DIRECTORY
        ),
        CLIENT_CACHE_SIZE
    )

    @Provides
    @Singleton
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Provides
    @Singleton
    internal fun provideCurlInterceptor(): CurlInterceptor {
        return CurlInterceptor(Loggable { message -> if (BuildConfig.DEBUG) println(message) })
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        context: Context,
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        curlInterceptor: CurlInterceptor,
        moshi: Moshi
    ): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .addInterceptor(curlInterceptor)
            .addInterceptor(ErrorHandlingInterceptor(NetworkHandler(context), moshi))
            .addInterceptor(RetryAfterInterceptor())
            .cache(cache)

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: Lazy<OkHttpClient>, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
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
