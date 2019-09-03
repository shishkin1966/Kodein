package shishkin.sl.kodeinpsb.sl.message

import shishkin.sl.kodeinpsb.sl.IDialogResultListener
import shishkin.sl.kodeinpsb.sl.action.DialogResultAction
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerSubscriber


class DialogResultMessage : AbsMessage {
    companion object {
        const val NAME = "DialogResultMessage"
    }

    private val action: DialogResultAction

    constructor(address: String, action: DialogResultAction) : super(address) {
        this.action = action
    }

    constructor(message: DialogResultMessage, action: DialogResultAction) : super(message) {
        this.action = action
    }

    override fun copy(): IMessage {
        return DialogResultMessage(this, action)
    }


    override fun getName(): String {
        return NAME
    }

    override fun read(subscriber: IMessengerSubscriber) {
        if (subscriber is IDialogResultListener) {
            (subscriber as IDialogResultListener).onDialogResult(action)
        }
    }

}