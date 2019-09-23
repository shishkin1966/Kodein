package shishkin.sl.kodeinpsb.app.screen.accounts

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Balance
import shishkin.sl.kodeinpsb.common.double2String
import shishkin.sl.kodeinpsb.common.trimZero
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist


class BalanceRecyclerViewAdapter : RecyclerView.Adapter<BalanceRecyclerViewAdapter.ViewHolder>() {

    private var _items: ArrayList<Balance> = ArrayList()

    init {
        setHasStableIds(false)
    }

    override fun getItemCount(): Int {
        return _items.size
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
        holder.bind(_items[position], _items.size)
    }

    fun setItems(items: List<Balance>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
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
                    ApplicationSpecialist.appContext.resources.getDimension(R.dimen.text_size_xlarge)
                )
            } else {
                balance?.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    ApplicationSpecialist.appContext.resources.getDimension(R.dimen.text_size)
                )
            }
        }
    }
}

