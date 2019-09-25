package shishkin.sl.kodeinpsb.app.screen.sidemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.action.HideSideMenuAction
import shishkin.sl.kodeinpsb.app.screen.accounts.BalanceRecyclerViewAdapter
import shishkin.sl.kodeinpsb.app.screen.main.MainPresenter
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.ui.AbsFragment


class SideMenuFragment : AbsFragment(), View.OnClickListener {
    companion object {
        const val NAME = "SideMenuFragment"

        fun newInstance(): SideMenuFragment {
            return SideMenuFragment()
        }
    }

    private var balanceView: RecyclerView? = null
    private val balanceAdapter: BalanceRecyclerViewAdapter = BalanceRecyclerViewAdapter()

    override fun createModel(): IModel {
        return SideMenuModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.RefreshViews -> {
                    refreshViews(action.getData() as SideMenuData?)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sidemenu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        balanceView = findView(R.id.balance_list)
        balanceView?.layoutManager = LinearLayoutManager(activity)
        balanceView?.adapter = balanceAdapter

        findView<View>(R.id.exchange_rates)?.setOnClickListener(this)
        findView<View>(R.id.exchange_cryptorates)?.setOnClickListener(this)
        findView<View>(R.id.address)?.setOnClickListener(this)
        findView<View>(R.id.setting)?.setOnClickListener(this)
        findView<View>(R.id.accounts)?.setOnClickListener(this)
        findView<View>(R.id.contact)?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        balanceView?.adapter = null
    }

    private fun refreshViews(viewData: SideMenuData?) {
        if (viewData == null) return

        balanceAdapter.setItems(viewData.balance)
    }

    override fun onClick(v: View?) {
        val presenter =
            ApplicationSingleton.instance.getPresenter<MainPresenter>(MainPresenter.NAME)
        presenter?.addAction(HideSideMenuAction())

        when (v?.id) {
            R.id.accounts -> getModel<SideMenuModel>()?.getPresenter<SideMenuPresenter>()?.addAction(
                ApplicationAction(
                    SideMenuPresenter.ShowAccounts
                )
            )

            R.id.setting -> getModel<SideMenuModel>()?.getPresenter<SideMenuPresenter>()?.addAction(
                ApplicationAction(
                    SideMenuPresenter.ShowSetting
                )
            )

            R.id.address -> getModel<SideMenuModel>()?.getPresenter<SideMenuPresenter>()?.addAction(
                ApplicationAction(
                    SideMenuPresenter.ShowAddress
                )
            )

            R.id.exchange_rates -> getModel<SideMenuModel>()?.getPresenter<SideMenuPresenter>()?.addAction(
                ApplicationAction(
                    SideMenuPresenter.ShowExchangeRates
                )
            )

            R.id.exchange_cryptorates -> getModel<SideMenuModel>()?.getPresenter<SideMenuPresenter>()?.addAction(
                ApplicationAction(
                    SideMenuPresenter.ShowDigitalRates
                )
            )

            R.id.contact -> getModel<SideMenuModel>()?.getPresenter<SideMenuPresenter>()?.addAction(
                ApplicationAction(
                    SideMenuPresenter.ShowContact
                )
            )
        }
    }

    override fun getName(): String {
        return NAME
    }

}
