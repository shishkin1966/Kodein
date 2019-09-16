package shishkin.sl.kodeinpsb.app.request

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.db.Db
import shishkin.sl.kodeinpsb.sl.request.AbsResultMessageRequest

class GetAccountsRequest(subscriber: String) : AbsResultMessageRequest<List<Account>>(subscriber) {
    companion object {
        const val NAME = "GetAccountsRequest"
    }

    override fun getData(): List<Account> {
        return ApplicationSingleton.instance.getDbProvider()!!.getDb<Db>()!!.getDao().getAccounts()
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
