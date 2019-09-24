package shishkin.sl.kodeinpsb.app.screen.sidemenu

import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.data.Balance
import shishkin.sl.kodeinpsb.app.provider.Provider
import shishkin.sl.kodeinpsb.app.request.GetBalanceRequest
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.action.HideProgressBarAction
import shishkin.sl.kodeinpsb.sl.action.ShowMessageAction
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

}
