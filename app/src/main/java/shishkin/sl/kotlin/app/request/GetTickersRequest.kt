package shishkin.sl.kotlin.app.request

import io.reactivex.Single
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Ticker
import shishkin.sl.kotlin.sl.request.AbsNetResultMessageRequest

class GetTickersRequest(subscribe: String) : AbsNetResultMessageRequest(subscribe) {
    companion object {
        const val NAME = "GetTickersRequest"
    }

    override fun getData(): Single<List<Ticker>>? {
        return ApplicationSingleton.instance.getApi().tickers
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
