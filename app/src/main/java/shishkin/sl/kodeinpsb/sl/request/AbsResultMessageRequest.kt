package shishkin.sl.kodeinpsb.sl.request

import shishkin.sl.kodeinpsb.sl.data.ExtError
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.message.ResultMessage
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerUnion
import shishkin.sl.kodeinpsb.sl.specialist.MessengerUnion
import java.util.*


abstract class AbsResultMessageRequest<T>() : AbsRequest(),
    IResultMessageRequest {

    private lateinit var _owner: String
    private var _copyTo: List<String> = ArrayList<String>()
    private var _error: ExtError? = null

    constructor(owner: String) : this() {
        _owner = owner
    }

    override fun validate(): Boolean {
        return !isCancelled()
    }

    override fun getOwner(): String {
        return _owner
    }

    override fun getCopyTo(): List<String> {
        return _copyTo
    }

    override fun setCopyTo(copyTo: List<String>) {
        this._copyTo = copyTo
    }

    override fun getError(): ExtError? {
        return _error
    }

    override fun setError(error: ExtError?) {
        _error = error
    }

    override fun response() {
        if (validate()) {
            val result = ExtResult().setName(getName()).setData(getData()).setError(getError())
            val union =
                ApplicationSpecialist.serviceLocator?.get<IMessengerUnion>(MessengerUnion.NAME);
            union?.addNotMandatoryMessage(
                ResultMessage(
                    getOwner(),
                    result
                ).setCopyTo(getCopyTo())
            );
        }
    }

    abstract fun getData() : T
}