package shishkin.sl.kotlin.sl.task

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import shishkin.sl.kotlin.common.Connectivity
import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.observe.NetObservable
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.provider.IObservableSubscriber
import shishkin.sl.kotlin.sl.provider.ObservableUnion
import shishkin.sl.kotlin.sl.request.IRequest
import shishkin.sl.kotlin.sl.state.State
import java.util.concurrent.BlockingQueue
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.TimeUnit


class NetExecutor : AbsRequestExecutor(), IObservableSubscriber {
    companion object {
        const val NAME = "NetExecutor"
    }

    private val QUEUE_CAPACITY = 1024
    private var threadCount = 2
    private var maxThreadCount = 2
    private val keepAliveTime: Long = 10 // 10 мин
    private val unit = TimeUnit.MINUTES
    private val queue = PriorityBlockingQueue<IRequest>(QUEUE_CAPACITY)
    private val executor = RequestThreadPoolExecutor(
        threadCount,
        maxThreadCount,
        keepAliveTime,
        unit,
        queue as BlockingQueue<Runnable>
    );

    override fun getExecutor(): RequestThreadPoolExecutor {
        return executor
    }

    override fun getName(): String {
        return NAME
    }

    override fun getObservable(): List<String> {
        return listOf(NetObservable.NAME);
    }

    override fun onChange(name: String, obj: Any) {
        if (name == NetObservable.NAME) {
            setThreadCount();
        }
    }

    override fun getState(): Int {
        return State.STATE_READY;
    }

    override fun setState(state: Int) {
    }

    override fun onStopProvider(provider: IProvider) {
    }

    override fun getProviderSubscription(): List<String> {
        return listOf(ObservableUnion.NAME)
    }

    private fun setThreadCount() {
        val context = ApplicationProvider.appContext
        setThreadCount(Connectivity.getActiveNetworkInfo(context))
    }

    private fun setThreadCount(info: NetworkInfo?) {
        if (info == null) {
            threadCount = 2
            maxThreadCount = 2
            return
        }

        when (info.type) {
            ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_WIMAX, ConnectivityManager.TYPE_ETHERNET -> {
                threadCount = 6
                maxThreadCount = 6
                return
            }

            ConnectivityManager.TYPE_MOBILE -> when (info.subtype) {
                TelephonyManager.NETWORK_TYPE_LTE  // 4G
                    , TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_EHRPD -> {
                    threadCount = 4
                    maxThreadCount = 4
                    return
                }

                TelephonyManager.NETWORK_TYPE_UMTS // 3G
                    , TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_EVDO_B -> {
                    threadCount = 2
                    maxThreadCount = 2
                    return
                }

                TelephonyManager.NETWORK_TYPE_GPRS // 2G
                    , TelephonyManager.NETWORK_TYPE_EDGE -> {
                    threadCount = 1
                    maxThreadCount = 1
                    return
                }

                else -> {
                    threadCount = 2
                    maxThreadCount = 2
                    return
                }
            }

        }
    }


}
