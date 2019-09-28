package shishkin.sl.kodeinpsb.app.request

import io.reactivex.Single
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Ticker
import shishkin.sl.kodeinpsb.app.provider.NetProvider
import shishkin.sl.kodeinpsb.sl.request.AbsNetResultMessageRequest

class GetTickersRequest(subscribe: String) : AbsNetResultMessageRequest(subscribe) {
    companion object {
        const val NAME = "GetTickersRequest"
    }

    override fun getData(): Single<List<Ticker>>? {
        val provider = ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
        return provider?.getApi()?.tickers
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
