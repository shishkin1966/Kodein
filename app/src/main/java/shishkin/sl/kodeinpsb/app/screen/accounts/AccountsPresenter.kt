package shishkin.sl.kodeinpsb.app.screen.accounts

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.provider.Provider
import shishkin.sl.kodeinpsb.app.request.GetAccountsRequest
import shishkin.sl.kodeinpsb.app.screen.create_account.CreateAccountFragment
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.IRouter
import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.action.*
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.presenter.AbsPresenter
import shishkin.sl.kodeinpsb.sl.request.IResponseListener
import shishkin.sl.kodeinpsb.sl.ui.IActivity


class AccountsPresenter(model: AccountsModel) : AbsPresenter(model), IResponseListener {
    companion object {
        const val NAME = "AccountsPresenter"
        const val OnClickCreateAccount = "OnClickCreateAccount"
        const val OnClickAccount = "OnClickAccount"
    }

    private var data: AccountsData? = null

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        if (data == null) {
            getData()
        } else {
            getView<AccountsFragment>()
                ?.addAction(DataAction(Actions.RefreshViews, data))
        }
    }

    private fun getData() {
        getView<AccountsFragment>()?.addAction(ShowProgressBarAction())
        Provider.getAccounts(NAME)
    }

    override fun response(result: ExtResult) {
        getView<AccountsFragment>()?.addAction(HideProgressBarAction());
        if (!result.hasError()) {
            when (result.getName()) {
                GetAccountsRequest.NAME -> {
                    data?.accounts = result.getData() as List<Account>
                    getModel<AccountsModel>()?.getView<AccountsFragment>()
                        ?.addAction(DataAction(Actions.RefreshViews, data))
                }
            }
        } else {
            getView<AccountsFragment>()
                ?.addAction(ShowMessageAction(result.getErrorText()!!).setType(ApplicationUtils.MESSAGE_TYPE_ERROR))
        }
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                OnClickAccount -> {
                    return true
                }
            }
        }

        if (action is ApplicationAction) {
            when (action.getName()) {
                OnClickCreateAccount -> {
                    createAccount()
                    return true
                }
            }
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:" + action.toString(),
            true
        );
        return false
    }

    private fun createAccount() {
        val activity = getView<AccountsFragment>()?.activity
        if (activity != null && activity is IRouter && activity.isValid()) {
            activity.showFragment(CreateAccountFragment.newInstance())
        }
    }

}
