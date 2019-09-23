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
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter


class AccountsRecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var _items: ArrayList<Account> = ArrayList()

    override fun getItemCount(): Int {
        return _items.size
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
        holder.bind(_items.get(position))
    }

    fun setItems(items: List<Account>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

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
        friendlyNameView?.setText(item.friendlyName)
        balanceView?.setText("${item.balance}  ${item.currency}")
        layout?.setOnClickListener(View.OnClickListener {
            val presenter =
                ApplicationSingleton.instance.getPresenter<IPresenter>(AccountsPresenter.NAME)
            presenter?.addAction(DataAction(AccountsPresenter.OnClickAccount, item))
        })
    }

}
