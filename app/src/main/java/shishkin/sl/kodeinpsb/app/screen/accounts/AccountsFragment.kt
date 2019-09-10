package shishkin.sl.kodeinpsb.app.screen.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.FragmentActionHandler
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.ui.AbsFragment


class AccountsFragment : AbsFragment() {
    companion object {
        fun newInstance(): AccountsFragment {
            return AccountsFragment()
        }
    }

    private val actionHandler = FragmentActionHandler(this)

    override fun createModel(): IModel {
        return AccountsModel(this)
    }

     override fun onAction(action: IAction): Boolean {
        if (!validate()) return false

        if (actionHandler.onAction(action)) return true

        ApplicationSingleton.instance.onError(getName(), "Unknown action:" + action.toString(), true);
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accounts, container, false)
    }

}