package shishkin.sl.kodeinpsb.sl.observe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist


abstract class AbsBroadcastReceiverObservable : AbsObservable() {
    private var intentFilter: IntentFilter = getIntentFilter()
    private var broadcastReceiver: BroadcastReceiver? = null

    abstract fun getIntentFilter(): IntentFilter

    override fun register() {
        val context = ApplicationSpecialist.instance
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                onChange(this as IObservable, intent)
            }
        }
        context.registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun unregister() {
        if (broadcastReceiver != null) {
            val context = ApplicationSpecialist.instance;
            if (broadcastReceiver != null) {
                context.unregisterReceiver(broadcastReceiver);
                broadcastReceiver = null;
            }
        }
    }
}
