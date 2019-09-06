package shishkin.sl.kodeinpsb.sl.observe

import android.content.IntentFilter
import android.net.ConnectivityManager



class NetworkBroadcastReceiverObservable : AbsBroadcastReceiverObservable() {
    companion object {
        const val NAME = "NetworkBroadcastReceiverObservable"
    }

    override fun getIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        return intentFilter
    }

    override fun getName(): String {
        return NAME
    }


}
