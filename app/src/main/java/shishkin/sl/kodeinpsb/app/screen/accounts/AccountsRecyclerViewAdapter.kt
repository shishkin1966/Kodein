package shishkin.sl.kodeinpsb.app.screen.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.common.double2String
import shishkin.sl.kodeinpsb.common.recyclerview.AbsRecyclerViewAdapter
import shishkin.sl.kodeinpsb.common.trimZero
import shishkin.sl.kodeinpsb.sl.action.DataAction


class AccountsRecyclerViewAdapter :
    AbsRecyclerViewAdapter<Account, AccountsRecyclerViewAdapter.ViewHolder>() {

    init {
        setHasStableIds(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_account,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var friendlyNameView: TextView? = null
        private var balanceView: TextView? = null
        private var layout: LinearLayout? = null

        init {
            friendlyNameView = itemView.findViewById(R.id.friendlyNameView)
            balanceView = itemView.findViewById(R.id.balanceView)
            layout = itemView.findViewById(R.id.layout)
        }

        fun bind(item: Account) {
            friendlyNameView?.text = item.friendlyName
            balanceView?.text = "${item.balance?.double2String()?.trimZero()} ${item.currency}"
            layout?.setOnClickListener {
                val presenter =
                    ApplicationSingleton.instance.getPresenter<AccountsPresenter>(AccountsPresenter.NAME)
                presenter?.addAction(DataAction(AccountsPresenter.OnClickAccount, item))
            }
        }

    }
}

