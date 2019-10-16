package shishkin.sl.kotlin.app.request

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Account
import shishkin.sl.kotlin.app.db.Db
import shishkin.sl.kotlin.sl.request.AbsResultMessageRequest

class GetAccountsRequest(subscriber: String) : AbsResultMessageRequest(subscriber) {
    companion object {
        const val NAME = "GetAccountsRequest"
    }

    override fun getData(): List<Account> {
        return ApplicationSingleton.instance.getDbProvider().getDb<Db>()!!.getDao().getAccounts()
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
