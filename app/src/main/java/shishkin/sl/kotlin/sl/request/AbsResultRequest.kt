package shishkin.sl.kotlin.sl.request

import shishkin.sl.kotlin.sl.data.ExtError
import shishkin.sl.kotlin.sl.data.ExtResult
import java.lang.ref.WeakReference
import java.util.*


abstract class AbsResultRequest<T> : AbsRequest, IResultRequest {

    private lateinit var ref: WeakReference<IResponseListener>
    private var copyTo: List<String> = ArrayList()

    private constructor() : super()

    constructor(owner: IResponseListener) : this() {
        ref = WeakReference(owner)
    }

    override fun getOwner(): IResponseListener? {
        return ref.get()
    }

    override fun isValid(): Boolean {
        return ref.get() != null && ref.get()!!.isValid() && !isCancelled()
    }

    override fun run() {
        if (isValid()) {
            try {
                getOwner()?.response(
                    ExtResult().setName(getName()).setVersion(getVersion()).setData(
                        getData()
                    )
                )
            } catch (e: Exception) {
                getOwner()?.response(
                    ExtResult().setName(getName()).setVersion(getVersion()).setError(
                        ExtError().addError(
                            getName(),
                            e.localizedMessage
                        )
                    )
                )
            }
        }
    }

    override fun getCopyTo(): List<String> {
        return copyTo
    }

    override fun setCopyTo(copyTo: List<String>) {
        this.copyTo = copyTo
    }

    abstract fun getData(): T?

}
