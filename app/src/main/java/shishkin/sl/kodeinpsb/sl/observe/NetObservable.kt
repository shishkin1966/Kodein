package shishkin.sl.kodeinpsb.sl.observe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.common.Connectivity
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist


class NetObservable : AbsObservable() {
    companion object {
        const val NAME = "NetObservable"
    }

    private var broadcastReceiver: BroadcastReceiver? = null

    init {
        if (ApplicationUtils.hasLollipop()) {
            val builder = NetworkRequest.Builder()
            builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_VPN)

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    onChange(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)

                    onChange(false)
                }
            }

            val connectivityManager = ApplicationUtils.getSystemService<ConnectivityManager>(
                ApplicationSpecialist.instance.applicationContext,
                Context.CONNECTIVITY_SERVICE
            )
            connectivityManager.registerNetworkCallback(builder.build(), callback)
        } else {
            val intentFilter = IntentFilter()
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            val context = ApplicationSpecialist.instance.applicationContext
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (Connectivity.isNetworkConnected(context)) {
                        onChange(true)
                    } else {
                        onChange(false)
                    }
                }
            }
            context.registerReceiver(broadcastReceiver, intentFilter)
        }
    }


    override fun getName(): String {
        return NAME
    }

}
