package shishkin.sl.kodeinpsb.sl.action

import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter

class PresenterActionHandler<M:IModel> : IActionHandler {
    private val presenter: IPresenter<M>

    constructor(presenter: IPresenter<M>) {
        this.presenter = presenter
    }

    override fun onAction(action: IAction): Boolean {
        if (action == null) return true
        if (!presenter.validate()) return false

        if (action is IMessage) {
            (action as IMessage).read(presenter)
            return true
        }
        return false
    }

}
