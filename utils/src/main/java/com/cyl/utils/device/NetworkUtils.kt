package com.cyl.utils.device

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

/**
 * 如果要实时监听网络变化，就需要使用
 * @see register
 * 如果只要获取当前网络连接状态，就使用
 * @see isAvailable
 * 获取当前连接网络类型，就使用
 * @see getConnectedType
 */

object NetworkUtils {
    private val listeners: ArrayList<OnNetworkChangedListener> = arrayListOf()
    private var netAvailable = false
    private var netType = NetWorkType.NONE

    fun register(context: Context, listener: OnNetworkChangedListener? = null) {
        val request = NetworkRequest.Builder().build()
        getSystemServer(context).registerNetworkCallback(request, callback)
        listener?.let { addListener(it) }
    }

    fun unregister(context: Context) {
        getSystemServer(context).unregisterNetworkCallback(callback)
        removeAllListeners()
    }

    fun addListener(listener: OnNetworkChangedListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    fun removeListener(listener: OnNetworkChangedListener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
        }
    }

    fun removeAllListeners() {
        listeners.forEach { listeners.remove(it) }
    }

    // 网络是否可用
    @JvmStatic
    fun isAvailable(context: Context): Boolean {
        val manager = getSystemServer(context)
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    /**
     * 连接网络类型
     */
    @JvmStatic
    fun getConnectedType(context: Context): NetWorkType {
        val manager = getSystemServer(context)
        var type = NetWorkType.NONE
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork) ?: return NetWorkType.NONE
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            type = NetWorkType.CELLULAR
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            type = NetWorkType.WIFI
        }
        return type
    }

    private fun getSystemServer(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val callback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            netAvailable = true
            listeners.forEach { it.onNetworkConnected(network) }
        }

        override fun onUnavailable() {
            super.onUnavailable()
            netAvailable = false
            listeners.forEach { it.onNetworkUnavailable() }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            netAvailable = false
            listeners.forEach { it.onNetworkDisconnected(network) }
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    netType = NetWorkType.WIFI
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    netType = NetWorkType.CELLULAR
                }
            } else {
                netType = NetWorkType.NONE
            }
        }
    }
}

enum class NetWorkType {
    WIFI, CELLULAR, NONE
}

interface OnNetworkChangedListener {
    /**
     * 连接成功
     */
    fun onNetworkConnected(network: Network) {}

    /**
     * 连接断开
     */
    fun onNetworkDisconnected(network: Network) {}

    /**
     * 连接成功但网络不可用
     */
    fun onNetworkUnavailable() {}
}