package shishkin.sl.kotlin.app.screen.sidemenu

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Account
import shishkin.sl.kotlin.app.data.Balance
import shishkin.sl.kotlin.app.provider.Providers
import shishkin.sl.kotlin.app.request.GetBalanceRequest
import shishkin.sl.kotlin.app.screen.accounts.AccountsFragment
import shishkin.sl.kotlin.app.screen.contact.ContactFragment
import shishkin.sl.kotlin.app.screen.digital_currencies.DigitalCurrenciesFragment
import shishkin.sl.kotlin.app.screen.map.MapFragment
import shishkin.sl.kotlin.app.screen.scanner.ScannerFragment
import shishkin.sl.kotlin.app.screen.setting.SettingFragment
import shishkin.sl.kotlin.app.screen.val_curs.ValCursFragment
import shishkin.sl.kotlin.sl.action.Actions
import shishkin.sl.kotlin.sl.action.ApplicationAction
import shishkin.sl.kotlin.sl.action.DataAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.data.ExtResult
import shishkin.sl.kotlin.sl.observe.IObjectObservableSubscriber
import shishkin.sl.kotlin.sl.observe.ObjectObservable
import shishkin.sl.kotlin.sl.presenter.AbsModelPresenter
import shishkin.sl.kotlin.sl.provider.ObservableUnion
import shishkin.sl.kotlin.sl.request.IResponseListener


class SideMenuPresenter(model: SideMenuModel) : AbsModelPresenter(model), IResponseListener,
    IObjectObservableSubscriber {

    companion object {
        const val NAME = "SideMenuPresenter"
        const val ShowAccounts = "ShowAccounts"
        const val ShowSetting = "ShowSetting"
        const val ShowScanner = "ShowScanner"
        const val ShowAddress = "ShowAddress"
        const val ShowExchangeRates = "ShowExchangeRates"
        const val ShowDigitalRates = "ShowDigitalRates"
        const val ShowContact = "ShowContact"
    }

    private lateinit var data: SideMenuData

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        if (!::data.isInitialized) {
            data = SideMenuData()
            getData()
        } else {
            getView<SideMenuFragment>().addAction(DataAction(Actions.RefreshViews, data))
        }
    }

    private fun getData() {
        Providers.getBalance(NAME)
    }

    override fun response(result: ExtResult) {
        if (!result.hasError()) {
            when (result.getName()) {
                GetBalanceRequest.NAME -> {
                    data.balance = result.getData() as List<Balance>
                    getView<SideMenuFragment>().addAction(DataAction(Actions.RefreshViews, data))
                }
            }
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

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            val router = ApplicationSingleton.instance.routerProvider
            when (action.getName()) {
                ShowAccounts -> {
                    if (!router.isCurrentFragment(AccountsFragment.NAME)) {
                        router.switchToTopFragment()
                    }
                    return true
                }

                ShowAddress -> {
                    if (!router.isCurrentFragment(MapFragment.NAME)) {
                        router.showFragment(MapFragment.newInstance())
                    }
                    return true
                }

                ShowExchangeRates -> {
                    if (!router.isCurrentFragment(ValCursFragment.NAME)) {
                        router.showFragment(ValCursFragment.newInstance())
                    }
                    return true
                }

                ShowDigitalRates -> {
                    if (!router.isCurrentFragment(DigitalCurrenciesFragment.NAME)) {
                        router.showFragment(DigitalCurrenciesFragment.newInstance())
                    }
                    return true
                }

                ShowContact -> {
                    if (!router.isCurrentFragment(ContactFragment.NAME)) {
                        router.showFragment(ContactFragment.newInstance())
                    }
                    return true
                }

                ShowSetting -> {
                    if (!router.isCurrentFragment(SettingFragment.NAME)) {
                        router.showFragment(SettingFragment.newInstance())
                    }
                    return true
                }

                ShowScanner -> {
                    if (!router.isCurrentFragment(ScannerFragment.NAME)) {
                        router.showFragment(ScannerFragment.newInstance())
                    }
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
}
