package com.adesso.movee.internal.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

interface NetworkState {
    val isConnected: Boolean
    val networkMap: Map<String, Network>
}

internal class NetworkStateImp : NetworkState {
    override var isConnected: Boolean = false
    override var networkMap: HashMap<String, Network> = hashMapOf()
}

// TODO : network call adapter içerisine inject edilmeli
object NetworkStateHolder : NetworkState {

    private lateinit var holder: NetworkStateImp

    override val isConnected: Boolean
        get() = holder.isConnected
    override val networkMap: HashMap<String, Network>
        get() = holder.networkMap

    fun Application.registerConnectivityMonitor() {
        holder = NetworkStateImp()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            NetworkCallbackImp(holder)
        )
    }
}

internal class NetworkCallbackImp(
    private val holder: NetworkStateImp
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        val networkId = network.toString()
        holder.networkMap[networkId] = network
        holder.isConnected = holder.networkMap.isNotEmpty()
    }

    override fun onLost(network: Network) {
        val networkId = network.toString()
        holder.networkMap.remove(networkId)
        holder.isConnected = holder.networkMap.isNotEmpty()
    }
}
