package shishkin.sl.kotlin.app.screen.accounts

import android.graphics.drawable.Drawable
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Account
import shishkin.sl.kotlin.app.data.Balance
import shishkin.sl.kotlin.app.provider.Providers
import shishkin.sl.kotlin.app.request.GetAccountsRequest
import shishkin.sl.kotlin.app.request.GetBalanceRequest
import shishkin.sl.kotlin.app.request.GetCurrencyRequest
import shishkin.sl.kotlin.app.screen.create_account.CreateAccountFragment
import shishkin.sl.kotlin.app.screen.view_account.ViewAccountFragment
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.sl.IRouterProvider
import shishkin.sl.kotlin.sl.action.*
import shishkin.sl.kotlin.sl.data.ExtResult
import shishkin.sl.kotlin.sl.observe.IObjectObservableSubscriber
import shishkin.sl.kotlin.sl.observe.ObjectObservable
import shishkin.sl.kotlin.sl.presenter.AbsModelPresenter
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.provider.ObservableUnion
import shishkin.sl.kotlin.sl.request.IResponseListener
import shishkin.sl.kotlin.sl.ui.AbsContentActivity


class AccountsPresenter(model: AccountsModel) : AbsModelPresenter(model), IResponseListener,
    IObjectObservableSubscriber {

    companion object {
        const val NAME = "AccountsPresenter"
        const val OnClickCreateAccount = "OnClickCreateAccount"
        const val OnClickAccount = "OnClickAccount"
        const val OnClickSort = "OnClickSort"
        const val OnClickFilter = "OnClickFilter"
        const val SortDialog = "SortDialog"
        const val FilterDialog = "FilterDialog"
    }

    private lateinit var data: AccountsData
    private val ALL = ApplicationProvider.appContext.getString(R.string.all)

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        if (!::data.isInitialized) {
            data = AccountsData()
            getData()
        } else {
            getView<AccountsFragment>().addAction(DataAction(Actions.RefreshViews, data))
        }
    }

    private fun getData() {
        getView<AccountsFragment>().addAction(ShowProgressBarAction())
        Providers.getAccounts(getName())
        Providers.getBalance(getName())
        Providers.getCurrency(getName())
    }

    override fun response(result: ExtResult) {
        getView<AccountsFragment>().addAction(HideProgressBarAction())
        if (!result.hasError()) {
            when (result.getName()) {
                GetAccountsRequest.NAME -> {
                    data.accounts = result.getData() as List<Account>
                    getView<AccountsFragment>().addAction(DataAction(Actions.RefreshViews, data))
                }
                GetBalanceRequest.NAME -> {
                    data.balance = result.getData() as List<Balance>
                    getView<AccountsFragment>().addAction(DataAction(Actions.RefreshViews, data))
                }
                GetCurrencyRequest.NAME -> {
                    data.currencies = result.getData() as List<String>
                }
            }
        } else {
            getView<AccountsFragment>().addAction(
                ShowMessageAction(result.getErrorText()!!).setType(
                    ApplicationUtils.MESSAGE_TYPE_ERROR
                )
            )
        }
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DialogClickAction) {
            onClickDialog(action.name, action.which)
            return true
        }

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
                OnClickSort -> {
                    onClickSort()
                    return true
                }
                OnClickFilter -> {
                    onClickFilter()
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
        val activity = getView<AccountsFragment>().activity
        if (activity != null && activity is IRouterProvider && activity.isValid()) {
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

    override fun getProviderSubscription(): List<String> {
        val list = ArrayList<String>(super.getProviderSubscription())
        list.add(ObservableUnion.NAME)
        return list
    }

    private fun viewAccount(account: Account) {
        val activity = getView<AccountsFragment>().activity
        if (activity != null) {
            (activity as AbsContentActivity).showFragment(
                ViewAccountFragment.newInstance(account),
                true
            )
        }
    }

    private fun onClickDialog(dialog: String, which: Int) {
        when (dialog) {
            SortDialog -> {
                data.sort = which
            }
            FilterDialog -> {
                if (which == 0) {
                    data.filter = null
                } else {
                    data.filter = data.currencies[which - 1]
                }
            }
        }
        getView<AccountsFragment>().addAction(DataAction(Actions.RefreshViews, data))
    }

    private fun onClickSort() {
        getView<AccountsFragment>().addAction(ApplicationAction(SortDialog))
    }

    private fun onClickFilter() {
        if (data.currencies.size > 1) {
            val items = arrayOfNulls<CharSequence>(data.currencies.size + 1)
            val icons = arrayOfNulls<Drawable>(data.currencies.size + 1)
            items[0] = ALL
            for (i in 0 until data.currencies.size) {
                items[i + 1] = data.currencies[i]
            }
            val map = HashMap<String, Any>()
            map["Items"] = items
            map["Icons"] = icons
            getView<AccountsFragment>().addAction(MapAction(FilterDialog, map))
        }
    }
}
