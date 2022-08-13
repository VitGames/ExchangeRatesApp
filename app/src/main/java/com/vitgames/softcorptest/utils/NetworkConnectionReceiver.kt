package com.vitgames.softcorptest.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


class NetworkConnectionReceiver(private val listener: NetworkConnectionListener) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        val isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting
        listener.onNetworkChange(isConnected)
    }
}

interface NetworkConnectionListener {
    fun onNetworkChange(isConnected: Boolean)
}

