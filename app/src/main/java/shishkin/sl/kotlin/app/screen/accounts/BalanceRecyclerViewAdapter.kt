package shishkin.sl.kotlin.app.screen.accounts

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.data.Balance
import shishkin.sl.kotlin.common.double2String
import shishkin.sl.kotlin.common.recyclerview.AbsRecyclerViewAdapter
import shishkin.sl.kotlin.common.trimZero
import shishkin.sl.kotlin.sl.provider.ApplicationProvider


class BalanceRecyclerViewAdapter :
    AbsRecyclerViewAdapter<Balance, BalanceRecyclerViewAdapter.ViewHolder>() {

    init {
        setHasStableIds(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_balance,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemCount)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var balance: TextView? = null

        init {
            balance = itemView.findViewById(R.id.balance)
        }

        fun bind(item: Balance, cnt: Int) {
            balance?.text = (item.balance?.double2String()?.trimZero() + " " + item.currency)
            if (cnt == 1) {
                balance?.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    ApplicationProvider.appContext.resources.getDimension(R.dimen.text_size_xlarge)
                )
            } else {
                balance?.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    ApplicationProvider.appContext.resources.getDimension(R.dimen.text_size)
                )
            }
        }
    }
}

