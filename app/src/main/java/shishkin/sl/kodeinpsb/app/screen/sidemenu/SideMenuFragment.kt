package shishkin.sl.kodeinpsb.app.screen.sidemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.screen.accounts.AccountsData
import shishkin.sl.kodeinpsb.app.screen.accounts.BalanceRecyclerViewAdapter
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.FragmentActionHandler
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.ui.AbsFragment


class SideMenuFragment : AbsFragment() {
    companion object {
        fun newInstance(): SideMenuFragment {
            return SideMenuFragment()
        }
    }

    private val actionHandler = FragmentActionHandler(this)
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

        if (actionHandler.onAction(action)) return true

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        );
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
    }

    override fun onDestroyView() {
        super.onDestroyView()

        balanceView?.adapter = null
    }

    private fun refreshViews(viewData: SideMenuData?) {
        if (viewData == null) return

        balanceAdapter.setItems(viewData.balance)
    }

}
