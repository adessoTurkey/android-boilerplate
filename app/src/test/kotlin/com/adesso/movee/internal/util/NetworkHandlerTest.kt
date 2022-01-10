package com.adesso.movee.internal.util

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adesso.movee.internal.util.NetworkStateHolder.registerConnectivityMonitor
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class NetworkHandlerTest {

    private val mockConnectivityManager: ConnectivityManager = mockk(relaxed = true)

    private val mockApplication: Application = mockk(relaxed = true) {
        every {
            getSystemService(any())
        } returns mockConnectivityManager
    }

    private val mockNetworkRequest: NetworkRequest = mockk(relaxed = true)

    private val mockNetwork1: Network = mockk(relaxed = true)

    private val mockNetwork2: Network = mockk(relaxed = true)

    @Before
    fun setUp() {
        mockkConstructor(NetworkRequest.Builder::class)
        every {
            anyConstructed<NetworkRequest.Builder>().build()
        } returns mockNetworkRequest
    }

    @After
    fun tearDown() {
        unmockkConstructor(NetworkRequest.Builder::class)
    }

    @Test
    fun `verify registration of network callback when registerConnectivityMonitor called`() {
        every {
            mockConnectivityManager.registerNetworkCallback(
                mockNetworkRequest,
                any(),
                any()
            )
        } returns Unit

        mockApplication.registerConnectivityMonitor()

        verify {
            mockConnectivityManager.registerNetworkCallback(
                mockNetworkRequest,
                any<NetworkCallbackImp>()
            )
        }
    }

    @Test
    fun `verify network map when callback returns onAvailable multiple times`() {
        val holder = NetworkStateImp()
        val networkCallback = NetworkCallbackImp(holder)
        networkCallback.onAvailable(mockNetwork1)
        networkCallback.onAvailable(mockNetwork2)
        assertEquals(
            mockNetwork1,
            holder.networkMap[mockNetwork1.toString()]
        )
        assertEquals(
            mockNetwork2,
            holder.networkMap[mockNetwork2.toString()]
        )
    }

    @Test
    fun `verify isConnected when an available network lost`() {
        val holder = NetworkStateImp()
        val networkCallback = NetworkCallbackImp(holder)
        assertEquals(
            false,
            holder.isConnected
        )
        networkCallback.onAvailable(mockNetwork1)
        assertEquals(
            true,
            holder.isConnected
        )
        networkCallback.onAvailable(mockNetwork2)
        assertEquals(
            true,
            holder.isConnected
        )
        networkCallback.onLost(mockNetwork1)
        assertEquals(
            true,
            holder.isConnected
        )
        networkCallback.onLost(mockNetwork2)
        assertEquals(
            false,
            holder.isConnected
        )
    }
}
