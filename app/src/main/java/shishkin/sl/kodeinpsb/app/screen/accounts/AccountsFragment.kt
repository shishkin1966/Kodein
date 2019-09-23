package shishkin.sl.kodeinpsb.app.screen.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.FragmentActionHandler
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.presenter.OnBackPressedPresenter
import shishkin.sl.kodeinpsb.sl.ui.AbsContentFragment


class AccountsFragment : AbsContentFragment(), View.OnClickListener {
    companion object {
        fun newInstance(): AccountsFragment {
            return AccountsFragment()
        }
    }

    private val actionHandler = FragmentActionHandler(this)
    private var accountsAdapter: AccountsRecyclerViewAdapter =
        AccountsRecyclerViewAdapter()
    private var accountsView: RecyclerView? = null
    private val onBackPressedPresenter = OnBackPressedPresenter()

    override fun createModel(): IModel {
        return AccountsModel(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findView<View>(R.id.create_account)?.setOnClickListener(this)
        findView<View>(R.id.sort_accounts)?.setOnClickListener(this)
        findView<View>(R.id.select_accounts)?.setOnClickListener(this)
        findView<View>(R.id.select_accounts_all)?.setOnClickListener(this)

        accountsView = findView(R.id.list)
        accountsView?.setLayoutManager(LinearLayoutManager(activity))
        accountsView?.setItemAnimator(DefaultItemAnimator())
        accountsView?.setAdapter(accountsAdapter)
    }

    override fun onStart() {
        super.onStart()

        addStateObserver(onBackPressedPresenter)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.RefreshViews -> {
                    refreshViews(action.getData() as AccountsData?)
                    return true
                }
            }
        }

        if (actionHandler.onAction(action)) return true

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:" + action.toString(),
            true
        );
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accounts, container, false)
    }

    private fun refreshViews(viewData: AccountsData?) {
        if (viewData == null) return

        accountsAdapter.setItems(viewData.getData())
        findView<View>(R.id.sort_accounts)?.setEnabled(viewData.isSortMenuEnabled())
        findView<View>(R.id.select_accounts)?.setEnabled(viewData.isFilterMenuEnabled())
        if (viewData.isFilterMenuEnabled() && !viewData.filter.isNullOrBlank()) {
            findView<View>(R.id.select_accounts_all_ll)?.setVisibility(View.VISIBLE)
        } else {
            findView<View>(R.id.select_accounts_all_ll)?.setVisibility(View.GONE)
        }
        //showAccountsBalance(viewData.balance)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        accountsView?.setAdapter(null)
    }

    override fun onClick(v: View?) {
        val presenter = getModel<AccountsModel>()?.getPresenter<AccountsPresenter>()

        when (v?.id) {
            R.id.create_account -> {
                presenter?.addAction(ApplicationAction(AccountsPresenter.OnClickCreateAccount))
            }
        }
    }

    override fun onBackPressed(): Boolean {
        onBackPressedPresenter.onClick()
        return true
    }

    override fun isTop(): Boolean {
        return true
    }


}
