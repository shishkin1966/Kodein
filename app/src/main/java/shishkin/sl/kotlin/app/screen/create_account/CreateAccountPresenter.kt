package shishkin.sl.kotlin.app.screen.create_account

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.action.CreateAccountTransaction
import shishkin.sl.kotlin.app.provider.Provider
import shishkin.sl.kotlin.sl.action.HideKeyboardAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.StopAction
import shishkin.sl.kotlin.sl.presenter.AbsModelPresenter


class CreateAccountPresenter(model: CreateAccountModel) : AbsModelPresenter(model) {
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
            getView<CreateAccountFragment>().addAction(HideKeyboardAction())
            getView<CreateAccountFragment>().addAction(StopAction())
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
