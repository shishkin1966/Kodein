package shishkin.sl.kodeinpsb.app.request

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.db.Db
import shishkin.sl.kodeinpsb.sl.request.AbsResultMessageRequest

class GetCurrencyRequest(subscriber: String) : AbsResultMessageRequest(subscriber) {
    companion object {
        const val NAME = "GetCurrencyRequest"
    }

    override fun getData(): List<String> {
        return ApplicationSingleton.instance.getDbProvider().getDb<Db>()?.getDao()!!.getCurrency()
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
