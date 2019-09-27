package shishkin.sl.kodeinpsb.app.screen.sidemenu

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.data.Balance
import shishkin.sl.kodeinpsb.app.provider.Provider
import shishkin.sl.kodeinpsb.app.request.GetBalanceRequest
import shishkin.sl.kodeinpsb.app.screen.accounts.AccountsFragment
import shishkin.sl.kodeinpsb.app.screen.contact.ContactFragment
import shishkin.sl.kodeinpsb.app.screen.digital_currencies.DigitalCurrenciesFragment
import shishkin.sl.kodeinpsb.app.screen.map.MapFragment
import shishkin.sl.kodeinpsb.app.screen.val_curs.ValCursFragment
import shishkin.sl.kodeinpsb.sl.IRouter
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.observe.IObjectObservableSubscriber
import shishkin.sl.kodeinpsb.sl.observe.ObjectObservable
import shishkin.sl.kodeinpsb.sl.presenter.AbsPresenter
import shishkin.sl.kodeinpsb.sl.request.IResponseListener
import shishkin.sl.kodeinpsb.sl.specialist.ObservableUnion


class SideMenuPresenter(model: SideMenuModel) : AbsPresenter(model), IResponseListener,
    IObjectObservableSubscriber {

    companion object {
        const val NAME = "SideMenuPresenter"
        const val ShowAccounts = "ShowAccounts"
        const val ShowSetting = "ShowSetting"
        const val ShowAddress = "ShowAddress"
        const val ShowExchangeRates = "ShowExchangeRates"
        const val ShowDigitalRates = "ShowDigitalRates"
        const val ShowContact = "ShowContact"
    }

    private var data: SideMenuData? = null

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        if (data == null) {
            data = SideMenuData()
            getData()
        } else {
            getView<SideMenuFragment>()
                ?.addAction(DataAction(Actions.RefreshViews, data))
        }
    }

    private fun getData() {
        Provider.getBalance(NAME)
    }

    override fun response(result: ExtResult) {
        if (!result.hasError()) {
            when (result.getName()) {
                GetBalanceRequest.NAME -> {
                    data?.balance = result.getData() as List<Balance>
                    getView<SideMenuFragment>()
                        ?.addAction(DataAction(Actions.RefreshViews, data))
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

    override fun getSpecialistSubscription(): List<String> {
        val list = ArrayList<String>()
        list.addAll(super.getSpecialistSubscription())
        list.add(ObservableUnion.NAME)
        return list
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            val router = getView<SideMenuFragment>()?.activity as IRouter
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

                /*
                ShowSetting -> {
                    if (!router.isCurrentFragment(SettingFragment.NAME)) {
                        router.showFragment(SettingFragment.newInstance())
                    }
                    return true
                }



                 */
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
