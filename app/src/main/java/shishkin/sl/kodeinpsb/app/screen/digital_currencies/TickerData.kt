package shishkin.sl.kodeinpsb.app.screen.digital_currencies

import com.google.common.collect.Collections2
import microservices.shishkin.example.data.Ticker
import java.util.*
import kotlin.collections.ArrayList


class TickerData {
    var tickers: ArrayList<Ticker>? = null
    var filter: String? = null

    fun getData(): List<Ticker> {
        if (tickers == null) return ArrayList()

        if (filter.isNullOrEmpty()) {
            tickers!!.sortWith(Comparator { o1, o2 -> o1.symbol!!.compareTo(o2.symbol!!,true) })
            return tickers!!
        } else {
            val list: ArrayList<Ticker> = ArrayList()
            list.addAll(Collections2.filter(tickers!!) { input ->
                input?.name?.contains(filter!!, true)!!
            })
            list.sortWith(Comparator { o1, o2 -> o1.symbol!!.compareTo(o2.symbol!!, true) })
            return list
        }
    }

}
