package shishkin.sl.kodeinpsb.app.paginator


import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.common.recyclerview.IPageListener
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.message.ResultMessage
import shishkin.sl.kodeinpsb.sl.request.IResponseListener
import shishkin.sl.kodeinpsb.sl.request.Request
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerSubscriber
import shishkin.sl.kodeinpsb.sl.specialist.MessengerUnion
import shishkin.sl.kodeinpsb.sl.state.IStateListener
import shishkin.sl.kodeinpsb.sl.state.State
import shishkin.sl.kodeinpsb.sl.state.StateObserver
import java.util.*


abstract class AbsPaginator(private var listener: String, pageSize: Int = PAGE_SIZE) :
    IResponseListener, IMessengerSubscriber, IStateListener, IPageListener {

    companion object {
        const val PAGE_SIZE = 20
    }

    private var currentPageSize = 0
    private var currentPosition = 0
    private lateinit var pageSize: MutableList<Int>
    private var isEof = false
    private val lifecycle = StateObserver(this)

    init {
        setPageSize(pageSize)
    }

    private fun getNextPageSize(): Int {
        if (pageSize.isEmpty()) return PAGE_SIZE

        for (i in pageSize.indices) {
            if (pageSize[i] > currentPageSize) {
                return pageSize[i]
            }
        }
        return pageSize[pageSize.size - 1]
    }

    private fun setPageSize(initialPageSize: Int) {
        var page = initialPageSize
        if (page <= 0) {
            page = PAGE_SIZE
        }
        pageSize = ArrayList()
        pageSize.add(page)
        pageSize.add(page * 2)
        pageSize.add(page * 4)
    }

    override fun hasData() {
        if (!isEof && isValid()) {
            currentPageSize = getNextPageSize()
            getData()
        }
    }

    private fun getData() {
        ApplicationSingleton.instance.execute(
            getRequest(
                getName(),
                currentPosition,
                currentPageSize
            )
        )
    }

    override fun response(result: ExtResult) {
        if (!isEof && isValid()) {
            if (!result.hasError()) {
                val list = result.getData() as List<*>
                if (list.isNotEmpty()) {
                    currentPosition += list.size
                } else {
                    isEof = true
                }
            }
            ApplicationSingleton.instance.addNotMandatoryMessage(ResultMessage(listener, result))
        }
    }

    abstract fun getRequest(name: String, currentPosition: Int, pageSize: Int): Request

    override fun isValid(): Boolean {
        return getState() == State.STATE_READY
    }

    override fun read(message: IMessage) {
    }

    override fun getSpecialistSubscription(): List<String> {
        return listOf(MessengerUnion.NAME)
    }

    override fun onStopSpecialist(specialist: ISpecialist) {
    }

    override fun getState(): Int {
        return lifecycle.getState()
    }

    override fun setState(state: Int) {
        lifecycle.setState(state)
    }

    override fun onCreateView() {
    }

    override fun onReadyView() {
        ApplicationSingleton.instance.registerSpecialistSubscriber(this)
        hasData()
    }

    override fun onDestroyView() {
        cancel()
        ApplicationSingleton.instance.unregisterSpecialistSubscriber(this)
    }

    private fun cancel() {
        ApplicationSingleton.instance.getExecutor().cancelRequests(getName())
    }

}
