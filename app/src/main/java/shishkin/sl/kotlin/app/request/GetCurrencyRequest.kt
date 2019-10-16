package shishkin.sl.kotlin.app.request

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.db.Db
import shishkin.sl.kotlin.sl.request.AbsResultMessageRequest

class GetCurrencyRequest(subscriber: String) : AbsResultMessageRequest(subscriber) {
    companion object {
        const val NAME = "GetCurrencyRequest"
    }

    override fun getData(): List<String> {
        return ApplicationSingleton.instance.getDao().getCurrency()
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
