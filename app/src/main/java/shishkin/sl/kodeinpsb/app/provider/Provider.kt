package shishkin.sl.kodeinpsb.app.provider

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.db.Db
import shishkin.sl.kodeinpsb.app.request.*
import shishkin.sl.kodeinpsb.common.getDir
import shishkin.sl.kodeinpsb.sl.provider.ErrorSingleton

class Provider {
    companion object {
        @JvmStatic
        fun getAccounts(subscriber: String) {
            ApplicationSingleton.instance.getDbProvider().request(GetAccountsRequest(subscriber))
        }

        @JvmStatic
        fun getBalance(subscriber: String) {
            ApplicationSingleton.instance.getDbProvider().request(GetBalanceRequest(subscriber))
        }

        @JvmStatic
        fun createAccount(account: Account) {
            ApplicationSingleton.instance.getDbProvider().request(CreateAccountRequest(account))
        }

        @JvmStatic
        fun getCurrency(subscriber: String) {
            ApplicationSingleton.instance.getDbProvider().request(GetCurrencyRequest(subscriber))
        }

        @JvmStatic
        fun getTickers(subscriber: String) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetTickersRequest(subscriber))
        }

        @JvmStatic
        fun checkDbCopy(): Boolean {
            return ApplicationSingleton.instance.getDbProvider()
                .checkCopy(Db.NAME, ErrorSingleton.instance.getPath().getDir())
        }

        @JvmStatic
        fun backupDb() {
            return ApplicationSingleton.instance.getDbProvider().backup(
                Db.NAME,
                ErrorSingleton.instance.getPath().getDir(),
                Db::class.java
            )
        }

        @JvmStatic
        fun restoreDb() {
            return ApplicationSingleton.instance.getDbProvider().restore(
                Db.NAME,
                ErrorSingleton.instance.getPath().getDir(),
                Db::class.java
            )
        }
    }
}
