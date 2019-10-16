package shishkin.sl.kotlin.sl.action.handler

import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.IActionHandler
import shishkin.sl.kotlin.sl.message.IMessage
import shishkin.sl.kotlin.sl.presenter.IPresenter

class PresenterActionHandler(private val presenter: IPresenter) : IActionHandler {

    override fun onAction(action: IAction): Boolean {
        if (!presenter.isValid()) return false

        if (action is IMessage) {
            action.read(presenter)
            return true
        }
        return false
    }

}
