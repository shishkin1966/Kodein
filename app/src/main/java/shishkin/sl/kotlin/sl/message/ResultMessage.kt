package shishkin.sl.kotlin.sl.message

import shishkin.sl.kotlin.sl.data.ExtResult
import shishkin.sl.kotlin.sl.provider.IMessengerSubscriber
import shishkin.sl.kotlin.sl.request.IResponseListener


class ResultMessage : AbsMessage {
    private lateinit var result: ExtResult

    private constructor(address: String) : super(address)

    constructor(address: String, result: ExtResult) : this(address) {
        this.result = result
    }

    private constructor(message: ResultMessage) : super(message)

    constructor(message: ResultMessage, result: ExtResult) : this(message) {
        this.result = result
    }

    override fun read(subscriber: IMessengerSubscriber) {
        if (subscriber is IResponseListener) {
            subscriber.response(result)
        }
    }

    override fun copy(): IMessage {
        return ResultMessage(this, result)
    }

    override fun isCheckDublicate(): Boolean {
        return true
    }

}
