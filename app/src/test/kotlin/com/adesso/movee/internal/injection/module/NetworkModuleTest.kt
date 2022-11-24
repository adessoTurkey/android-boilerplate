package com.adesso.movee.internal.injection.module

import com.adesso.movee.BuildConfig
import com.adesso.movee.data.remote.api.MovieService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.moczul.ok2curl.CurlInterceptor
import com.squareup.moshi.Moshi
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create

class NetworkModuleTest {

    private lateinit var networkModule: NetworkModule
    private lateinit var retrofit: Retrofit

    @Before
    fun `set up`() {
        networkModule = NetworkModule()
        retrofit = mockk()
    }

    @Test
    fun `verify provided logging interceptor`() {
        val interceptor = networkModule.provideLoggingInterceptor()
        val logLevel =
            if (BuildConfig.ENABLE_LOG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        assertEquals(logLevel, interceptor.level)
    }

    @Test
    fun `verify provided okhttp client for retrofit`() {
        val loggingInterceptor = mockk<HttpLoggingInterceptor>()
        val curlInterceptor = mockk<CurlInterceptor>()
        val chuckerInterceptor = mockk<ChuckerInterceptor>()

        val client = networkModule.provideOkHttpClient(
            loggingInterceptor,
            curlInterceptor,
            chuckerInterceptor,
        )

        with(client.interceptors) {
            assertTrue(contains(loggingInterceptor))
            assertTrue(contains(curlInterceptor))
            assertTrue(contains(chuckerInterceptor))
        }
    }

    @Test
    fun `verify provided retrofit`() {
        val client = mockk<dagger.Lazy<OkHttpClient>>()
        val moshi = mockk<Moshi>()

        val retrofit = networkModule.provideRetrofit(client, moshi)

        assertEquals(BuildConfig.BASE_URL, retrofit.baseUrl().toUrl().toString())
    }

    @Test
    fun `verify provided equity service`() {
        val movieService = mockk<MovieService>()

        every {
            retrofit.create<MovieService>()
        } returns movieService

        val actualService = networkModule.provideMovieService(retrofit)

        assertEquals(movieService, actualService)
    }
}
