package shishkin.sl.kodeinpsb.app.screen.digital_currencies

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.data.Ticker
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist


class TickerRecyclerViewAdapter : RecyclerView.Adapter<TickerRecyclerViewAdapter.ViewHolder>() {

    private val items: ArrayList<Ticker> = ArrayList()

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Ticker>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_ticker,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val symbol: TextView
        private val name: TextView
        private val money: TextView
        private val hours: TextView
        private val days: TextView

        init {
            symbol = itemView.findViewById(R.id.symbol)
            name = itemView.findViewById(R.id.name)
            money = itemView.findViewById(R.id.money)
            hours = itemView.findViewById(R.id.hours)
            days = itemView.findViewById(R.id.days)
        }

        fun bind(item: Ticker) {
            symbol.text = item.symbol
            name.text = item.name
            money.text = "${item.priceUsd}$"

            if (!item.percentChange24h.isNullOrEmpty()) {
                val s24h = SpannableString("24h: " + item.percentChange24h + "%")
                if (item.percentChange24h?.toFloat()!! > 0) {
                    s24h.setSpan(
                        ForegroundColorSpan(
                            ApplicationUtils.getColor(
                                ApplicationSpecialist.appContext,
                                R.color.green
                            )
                        ),
                        5,
                        6 + item.percentChange24h?.length!!,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    s24h.setSpan(
                        ForegroundColorSpan(
                            ApplicationUtils.getColor(
                                ApplicationSpecialist.appContext,
                                R.color.red
                            )
                        ),
                        5,
                        6 + item.percentChange24h?.length!!,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                hours.text = s24h
            }

            if (!item.percentChange7d.isNullOrEmpty()) {
                val s7d = SpannableString("7d: " + item.percentChange7d + "%")
                if (item.percentChange7d?.toFloat()!! > 0) {
                    s7d.setSpan(
                        ForegroundColorSpan(
                            ApplicationUtils.getColor(
                                ApplicationSpecialist.appContext,
                                R.color.green
                            )
                        ),
                        4,
                        5 + item.percentChange7d?.length!!,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    s7d.setSpan(
                        ForegroundColorSpan(
                            ApplicationUtils.getColor(
                                ApplicationSpecialist.appContext,
                                R.color.red
                            )
                        ),
                        4,
                        5 + item.percentChange7d?.length!!,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                days.text = s7d
            }
        }

    }

}
