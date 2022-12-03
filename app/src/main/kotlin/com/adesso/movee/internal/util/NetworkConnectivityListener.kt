package com.adesso.movee.internal.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

interface NetworkConnectivityListener {

    val isConnected: Boolean
    fun getConnectivityStatusFlow(): Flow<ConnectivityStatus>

    sealed interface ConnectivityStatus {
        object Connected : ConnectivityStatus
        object Disconnected : ConnectivityStatus
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkConnectivityListenerImpl(
    context: Context
) : NetworkConnectivityListener {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val availableNetworks: MutableMap<Long, Network> = mutableMapOf()

    override val isConnected: Boolean
        get() = availableNetworks.isNotEmpty()

    init {
        connectivityManager.registerDefaultNetworkCallback(
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    availableNetworks[network.networkHandle] = network
                }

                override fun onLost(network: Network) {
                    availableNetworks.remove(network.networkHandle)
                }
            }
        )
    }

    override fun getConnectivityStatusFlow(): Flow<NetworkConnectivityListener.ConnectivityStatus> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                availableNetworks[network.networkHandle] = network
                launch { send(NetworkConnectivityListener.ConnectivityStatus.Connected) }
            }

            override fun onLost(network: Network) {
                availableNetworks.remove(network.networkHandle)
                if (!isConnected) {
                    launch { send(NetworkConnectivityListener.ConnectivityStatus.Disconnected) }
                }
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}
