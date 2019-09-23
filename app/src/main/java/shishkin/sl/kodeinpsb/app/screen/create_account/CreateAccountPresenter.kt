package shishkin.sl.kodeinpsb.app.screen.create_account

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.action.CreateAccountTransaction
import shishkin.sl.kodeinpsb.app.provider.Provider
import shishkin.sl.kodeinpsb.sl.action.HideKeyboardAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.StopAction
import shishkin.sl.kodeinpsb.sl.presenter.AbsPresenter


class CreateAccountPresenter(model: CreateAccountModel) : AbsPresenter(model) {
    companion object {
        const val NAME = "CreateAccountPresenter"
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is CreateAccountTransaction) {
            Provider.createAccount(action.account)
            getView<CreateAccountFragment>()?.addAction(HideKeyboardAction())
            getView<CreateAccountFragment>()?.addAction(StopAction())
            return true
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

}
