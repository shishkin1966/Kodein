package shishkin.sl.kodeinpsb.app.provider

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.request.*

class Provider {
    companion object {
        @JvmStatic
        fun getAccounts(subscriber: String) {
            ApplicationSingleton.instance.getDbProvider()?.request(GetAccountsRequest(subscriber))
        }

        @JvmStatic
        fun getBalance(subscriber: String) {
            ApplicationSingleton.instance.getDbProvider()?.request(GetBalanceRequest(subscriber))
        }

        @JvmStatic
        fun createAccount(account: Account) {
            ApplicationSingleton.instance.getDbProvider()?.request(CreateAccountRequest(account))
        }

        @JvmStatic
        fun getCurrency(subscriber: String) {
            ApplicationSingleton.instance.getDbProvider()?.request(GetCurrencyRequest(subscriber))
        }

        @JvmStatic
        fun getTickers(subscriber: String) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetTickersRequest(subscriber))
        }
    }
}
