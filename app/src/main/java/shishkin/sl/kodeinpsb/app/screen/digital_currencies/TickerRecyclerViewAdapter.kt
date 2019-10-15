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
import shishkin.sl.kodeinpsb.common.recyclerview.AbsRecyclerViewAdapter
import shishkin.sl.kodeinpsb.sl.provider.ApplicationProvider


class TickerRecyclerViewAdapter :
    AbsRecyclerViewAdapter<Ticker, TickerRecyclerViewAdapter.ViewHolder>() {

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
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val symbol: TextView = itemView.findViewById(R.id.symbol)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val money: TextView = itemView.findViewById(R.id.money)
        private val hours: TextView = itemView.findViewById(R.id.hours)
        private val days: TextView = itemView.findViewById(R.id.days)

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
                                ApplicationProvider.appContext,
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
                                ApplicationProvider.appContext,
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
                                ApplicationProvider.appContext,
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
                                ApplicationProvider.appContext,
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
