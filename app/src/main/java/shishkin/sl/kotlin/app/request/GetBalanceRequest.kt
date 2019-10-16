package shishkin.sl.kotlin.app.request

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Balance
import shishkin.sl.kotlin.app.db.Db
import shishkin.sl.kotlin.sl.request.AbsResultMessageRequest

class GetBalanceRequest(subscriber: String) : AbsResultMessageRequest(subscriber) {
    companion object {
        const val NAME = "GetBalanceRequest"
    }

    override fun getData(): List<Balance> {
        return ApplicationSingleton.instance.getDbProvider()?.getDb<Db>()!!.getDao().getBalance()
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
