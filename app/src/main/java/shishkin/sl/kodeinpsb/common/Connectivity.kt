package shishkin.sl.kodeinpsb.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager


class Connectivity {
    companion object {
        /**
         * Indicates whether network connectivity exists and it is possible to establish
         * connections and pass data.
         *
         * Always call this before attempting to perform data transactions.
         *
         * @return `true` if network connectivity exists, `false` otherwise.
         */
        @JvmStatic
        fun isNetworkConnected(context: Context): Boolean {
            val activeNetwork = getActiveNetworkInfo(context)
            return isNetworkConnected(activeNetwork)
        }

        /**
         * Indicates whether network connectivity exists and it is possible to establish
         * connections and pass data.
         *
         * Always call this before attempting to perform data transactions.
         *
         * @return `true` if network connectivity exists, `false` otherwise.
         */
        @JvmStatic
        fun isNetworkConnected(activeNetwork: NetworkInfo): Boolean {
            return activeNetwork.isConnected
        }

        /**
         * Indicates whether network connectivity exists or is in the process
         * of being established. This is good for applications that need to
         * do anything related to the network other than read or write data.
         *
         * @return `true` if network connectivity exists or is in the process
         * of being established, `false` otherwise.
         */
        @JvmStatic
        fun isNetworkConnectedOrConnecting(context: Context): Boolean {
            val activeNetwork = getActiveNetworkInfo(context)
            return isNetworkConnectedOrConnecting(activeNetwork)
        }

        /**
         * Indicates whether network connectivity exists or is in the process
         * of being established. This is good for applications that need to
         * do anything related to the network other than read or write data.
         *
         * @return `true` if network connectivity exists or is in the process
         * of being established, `false` otherwise.
         */
        @JvmStatic
        fun isNetworkConnectedOrConnecting(activeNetwork: NetworkInfo): Boolean {
            return activeNetwork.isConnectedOrConnecting
        }

        /**
         * Returns details about the currently active default data network. When
         * connected, this network is the default route for outgoing connections.
         * You should always check [NetworkInfo.isConnected] before initiating
         * network traffic. This may return `null` when there is no default
         * network.
         *
         * This method requires the caller to hold the permission
         * [android.Manifest.permission.ACCESS_NETWORK_STATE].
         *
         * @return a [NetworkInfo] object for the current default network
         * or `null` if no default network is currently active
         */
        @JvmStatic
        fun getActiveNetworkInfo(context: Context): NetworkInfo {
            val connectivityManager = getConnectivityManager(context)
            return connectivityManager.activeNetworkInfo
        }

        @JvmStatic
        private fun getConnectivityManager(context: Context): ConnectivityManager {
            return ApplicationUtils.getSystemService(context, Context.CONNECTIVITY_SERVICE)
        }

        @JvmStatic
        fun isWiFiConnected(context: Context): Boolean {
            val wifiManager =
                ApplicationUtils.getSystemService<WifiManager>(context, Context.WIFI_SERVICE)
            if (wifiManager.isWifiEnabled) { // WiFi adapter is ON
                val wifiInfo = wifiManager.getConnectionInfo()
                return wifiInfo.getNetworkId() != -1
            } else {
                return false // WiFi adapter is OFF
            }
        }

    }

}
