package shishkin.sl.kotlin.app.screen.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Account
import shishkin.sl.kotlin.common.double2String
import shishkin.sl.kotlin.common.recyclerview.AbsRecyclerViewAdapter
import shishkin.sl.kotlin.common.trimZero
import shishkin.sl.kotlin.sl.action.DataAction


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

