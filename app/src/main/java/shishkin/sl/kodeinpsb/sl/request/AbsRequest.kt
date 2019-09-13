package shishkin.sl.kodeinpsb.sl.request

import shishkin.sl.kodeinpsb.sl.task.RequestThreadPoolExecutor


abstract class AbsRequest() : IRequest {

    private var _rank = Rank.MIDDLE_RANK
    private var _isCanceled = false
    private var _id = 0

    constructor(rank: Int) : this() {
        this._rank = rank
    }

    override fun getRank(): Int {
        return _rank
    }

    override fun setRank(rank: Int): IRequest {
        this._rank = rank
        return this
    }


    override fun isCancelled(): Boolean {
        return _isCanceled
    }

    override fun setCanceled() {
        _isCanceled = true
    }

    override fun validate(): Boolean {
        return !isCancelled()
    }

    override fun getId(): Int {
        return _id
    }

    override fun setId(id: Int): IRequest {
        this._id = id
        return this
    }

    override operator fun compareTo(o: IRequest): Int {
        return o.getRank() - this.getRank()
    }

    override fun getAction(oldRequest: IRequest): Int {
        return RequestThreadPoolExecutor.ACTION_DELETE
    }


}
