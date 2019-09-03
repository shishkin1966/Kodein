package shishkin.sl.kodeinpsb.sl.action

import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter

class PresenterActionHandler : IActionHandler {
    private val presenter: IPresenter

    constructor(presenter: IPresenter) {
        this.presenter = presenter
    }

    override fun onAction(action: IAction): Boolean {
        if (!presenter.validate()) return false

        if (action is IMessage) {
            action.read(presenter)
            return true
        }
        return false
    }

}