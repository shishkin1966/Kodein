package shishkin.sl.kodeinpsb.sl.request

import shishkin.sl.kodeinpsb.BuildConfig
import shishkin.sl.kodeinpsb.sl.task.RequestThreadPoolExecutor


abstract class AbsRequest() : IRequest {
    private var rank : Int = Rank.MIDDLE_RANK
    private var isCanceled = false
    private var id = 0
    private var version : Int = BuildConfig.VERSION_CODE

    override fun getRank(): Int {
        return rank
    }

    override fun setRank(rank: Int): IRequest {
        this.rank = rank
        return this
    }


    override fun isCancelled(): Boolean {
        return isCanceled
    }

    override fun setCanceled() {
        isCanceled = true
    }

    override fun isValid(): Boolean {
        return !isCancelled()
    }

    override fun getId(): Int {
        return id
    }

    override fun setId(id: Int): IRequest {
        this.id = id
        return this
    }

    override operator fun compareTo(other: IRequest): Int {
        return other.getRank() - getRank()
    }

    override fun getAction(oldRequest: IRequest): Int {
        return RequestThreadPoolExecutor.ACTION_DELETE
    }

    override fun getVersion(): Int {
        return version
    }

    override fun setVersion(version: Int): IRequest {
        this.version = version
        return this
    }


}
