package shishkin.sl.kodeinpsb.sl.action.handler

import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter

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
