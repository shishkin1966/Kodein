package shishkin.sl.kodeinpsb.app.screen.view_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.common.double2String
import shishkin.sl.kodeinpsb.common.formatDateShortRu
import shishkin.sl.kodeinpsb.common.trimZero
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.FragmentActionHandler
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.ui.AbsContentFragment


class ViewAccountFragment : AbsContentFragment() {
    companion object {
        const val NAME = "ViewAccountFragment"
        const val ACCOUNT = "ACCOUNT"

        fun newInstance(account: Account): ViewAccountFragment {
            val f = ViewAccountFragment()
            val bundle = Bundle()
            bundle.putParcelable(ACCOUNT, account)
            f.arguments = bundle
            return f
        }
    }

    private var account: Account? = null

    override fun createModel(): IModel {
        return ViewAccountModel(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        account = arguments?.getParcelable(ACCOUNT)
        if (account != null) {
            findView<TextView>(R.id.title)?.text = account?.friendlyName
            findView<TextView>(R.id.balanceView)?.text =
                "${account?.balance?.double2String()?.trimZero()} ${account?.currency}"
            findView<TextView>(R.id.openDateView)?.text =
                "${getString(R.string.fragment_account_open_date_format)}  ${account?.openDate?.formatDateShortRu()}"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_account, container, false)
    }

    override fun getName(): String {
        return NAME
    }

}

