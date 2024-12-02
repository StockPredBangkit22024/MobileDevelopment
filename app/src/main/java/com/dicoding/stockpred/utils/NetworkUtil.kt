package com.dicoding.stockpred.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build

object NetworkUtil {

    interface NetworkStateListener {
        fun onNetworkAvailable()
        fun onNetworkLost()
    }

    fun registerNetworkCallback(context: Context, listener: NetworkStateListener) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    listener.onNetworkAvailable()
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    listener.onNetworkLost()
                }
            }

            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                .build()

            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        } else {
            // For API level < 21, use BroadcastReceiver
            @Suppress("DEPRECATION")
            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            val networkReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    @Suppress("DEPRECATION")
                    val activeNetwork = connectivityManager.activeNetworkInfo
                    if (activeNetwork?.isConnected == true) {
                        listener.onNetworkAvailable()
                    } else {
                        listener.onNetworkLost()
                    }
                }
            }
            context.registerReceiver(networkReceiver, intentFilter)
        }
    }
}
