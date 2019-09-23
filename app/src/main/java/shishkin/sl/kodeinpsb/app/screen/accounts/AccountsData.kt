package shishkin.sl.kodeinpsb.app.screen.accounts

import com.google.common.collect.Collections2
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.data.Balance
import java.util.*
import kotlin.collections.ArrayList


class AccountsData {
    private var sort = 0
    var filter: String? = null
    var accounts: List<Account> = ArrayList<Account>()
    var currencies: List<String> = ArrayList<String>()
    var balance: List<Balance> = ArrayList<Balance>()
    var isShowMessage = false
    var message: String? = null
    var messageType: Int = 0
    private val nameComparator =
        { o1: Account, o2: Account -> o1.friendlyName!!.compareTo(o2.friendlyName!!) }
    private val currencyComparator =
        { o1: Account, o2: Account -> o1.currency.compareTo(o2.currency) }

    fun isSortMenuEnabled(): Boolean {
        return accounts.size > 1
    }

    fun isFilterMenuEnabled(): Boolean {
        return balance.size > 1
    }

    fun getData(): List<Account> {
        val list = ArrayList<Account>()
        if (filter.isNullOrBlank()) {
            list.addAll(accounts)
        } else {
            list.addAll(Collections2.filter(accounts) { value ->
                value?.currency.equals(filter)
            })
        }
        when (sort) {
            1 -> Collections.sort(list, nameComparator)

            2 -> Collections.sort(list, currencyComparator)
        }
        return list
    }

}
