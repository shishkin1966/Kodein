package shishkin.sl.kodeinpsb.app.screen.accounts

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.data.Balance
import shishkin.sl.kodeinpsb.app.provider.Provider
import shishkin.sl.kodeinpsb.app.request.GetAccountsRequest
import shishkin.sl.kodeinpsb.app.request.GetBalanceRequest
import shishkin.sl.kodeinpsb.app.screen.create_account.CreateAccountFragment
import shishkin.sl.kodeinpsb.app.screen.view_account.ViewAccountFragment
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.IRouter
import shishkin.sl.kodeinpsb.sl.action.*
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.observe.IObjectObservableSubscriber
import shishkin.sl.kodeinpsb.sl.observe.ObjectObservable
import shishkin.sl.kodeinpsb.sl.presenter.AbsPresenter
import shishkin.sl.kodeinpsb.sl.request.IResponseListener
import shishkin.sl.kodeinpsb.sl.specialist.ObservableUnion
import shishkin.sl.kodeinpsb.sl.ui.AbsContentActivity


class AccountsPresenter(model: AccountsModel) : AbsPresenter(model), IResponseListener,
    IObjectObservableSubscriber {

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
            data = AccountsData()
            getData()
        } else {
            getView<AccountsFragment>()
                ?.addAction(DataAction(Actions.RefreshViews, data))
        }
    }

    private fun getData() {
        getView<AccountsFragment>()?.addAction(ShowProgressBarAction())
        Provider.getAccounts(getName())
        Provider.getBalance(getName())
    }

    override fun response(result: ExtResult) {
        getView<AccountsFragment>()?.addAction(HideProgressBarAction())
        if (!result.hasError()) {
            when (result.getName()) {
                GetAccountsRequest.NAME -> {
                    data?.accounts = result.getData() as List<Account>
                    getView<AccountsFragment>()
                        ?.addAction(DataAction(Actions.RefreshViews, data))
                }
                GetBalanceRequest.NAME -> {
                    data?.balance = result.getData() as List<Balance>
                    getView<AccountsFragment>()
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
                    viewAccount(action.getData() as Account)
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
            "Unknown action:$action",
            true
        )
        return false
    }

    private fun createAccount() {
        val activity = getView<AccountsFragment>()?.activity
        if (activity != null && activity is IRouter && activity.isValid()) {
            activity.showFragment(CreateAccountFragment.newInstance())
        }
    }

    override fun getListenObjects(): List<String> {
        return listOf(Account.TABLE)
    }

    override fun getObservable(): List<String> {
        return listOf(ObjectObservable.NAME)
    }

    override fun onChange(name: String, obj: Any) {
        if (name == ObjectObservable.NAME) {
            when (obj.toString()) {
                Account.TABLE -> {
                    getData()
                }
            }
        }
    }

    override fun getSpecialistSubscription(): List<String> {
        val list = ArrayList<String>()
        list.addAll(super.getSpecialistSubscription())
        list.add(ObservableUnion.NAME)
        return list
    }

    private fun viewAccount(account: Account) {
        val activity = getView<AccountsFragment>()?.activity
        if (activity != null) {
            (activity as AbsContentActivity).showFragment(
                ViewAccountFragment.newInstance(account),
                true
            )
        }
    }

}
