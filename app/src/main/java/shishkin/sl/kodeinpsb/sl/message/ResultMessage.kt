package shishkin.sl.kodeinpsb.sl.message

import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.request.IResponseListener
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerSubscriber


class ResultMessage : AbsMessage {
     private lateinit var _result: ExtResult

    private constructor(address: String) : super(address)

    constructor(address: String, result: ExtResult): this(address) {
        _result = result
    }

    private constructor(message: ResultMessage) : super(message)

    constructor(message: ResultMessage, result: ExtResult) : this(message){
        _result = result
    }

    override fun read(subscriber: IMessengerSubscriber) {
        if (subscriber is IResponseListener) {
            ApplicationUtils.runOnUiThread(Runnable {
                subscriber.response(_result)
            })
        }
    }

    override fun copy(): IMessage {
        return ResultMessage(this, _result)
    }

    override fun isCheckDublicate(): Boolean {
        return true
    }

}