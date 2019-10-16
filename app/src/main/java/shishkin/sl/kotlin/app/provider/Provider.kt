package shishkin.sl.kotlin.app.provider

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Account
import shishkin.sl.kotlin.app.db.Db
import shishkin.sl.kotlin.app.request.*
import shishkin.sl.kotlin.common.getDir
import shishkin.sl.kotlin.sl.provider.ErrorSingleton

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
