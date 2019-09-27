package shishkin.sl.kodeinpsb.sl.request

import shishkin.sl.kodeinpsb.sl.task.RequestThreadPoolExecutor


abstract class AbsRequest() : IRequest {

    private var rank = Rank.MIDDLE_RANK
    private var isCanceled = false
    private var id = 0

    constructor(rank: Int) : this() {
        this.rank = rank
    }

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


}
