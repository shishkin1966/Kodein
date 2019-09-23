package shishkin.sl.kodeinpsb.app.request

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.data.Balance
import shishkin.sl.kodeinpsb.app.db.Db
import shishkin.sl.kodeinpsb.sl.request.AbsResultMessageRequest

class GetBalanceRequest(subscriber: String) : AbsResultMessageRequest<List<Balance>>(subscriber) {
    companion object {
        const val NAME = "GetBalanceRequest"
    }

    override fun getData(): List<Balance> {
        return ApplicationSingleton.instance.getDbProvider()!!.getDb<Db>()!!.getDao().getBalance()
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
