package shishkin.sl.kodeinpsb.sl.task

import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.observe.NetworkBroadcastReceiverObservable
import shishkin.sl.kodeinpsb.sl.request.IRequest
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber
import shishkin.sl.kodeinpsb.sl.specialist.ObservableUnion
import shishkin.sl.kodeinpsb.sl.state.State
import java.util.concurrent.BlockingQueue
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.TimeUnit


class NetExecutor : AbsRequestExecutor(), IObservableSubscriber {
    companion object {
        const val NAME = "NetExecutor"
    }

    private val QUEUE_CAPACITY = 1024
    private val threadCount = 2
    private val maxThreadCount = 2
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
        return listOf(NetworkBroadcastReceiverObservable.NAME);
    }

    override fun onChange(name: String, obj: Any) {
        if (name == NetworkBroadcastReceiverObservable.NAME) {
            setThreadCount();
        }
    }

    override fun getState(): Int {
        return State.STATE_READY;
    }

    override fun setState(state: Int) {
    }

    override fun onStop(specialist: ISpecialist) {
    }

    override fun getSpecialistSubscription(): List<String> {
        return listOf(ObservableUnion.NAME)
    }
}
