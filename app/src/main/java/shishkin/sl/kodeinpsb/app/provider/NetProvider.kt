package shishkin.sl.kodeinpsb.app.provider

import microservices.shishkin.example.net.NetApi
import shishkin.sl.kodeinpsb.sl.IProvider
import shishkin.sl.kodeinpsb.sl.provider.AbsNetProvider


class NetProvider : AbsNetProvider<NetApi>() {
    companion object {
        const val NAME = "NetProvider"
        const val URL = "https://api.coinmarketcap.com/" //Базовый адрес
    }

    override fun getApiClass(): Class<NetApi> {
        return NetApi::class.java
    }

    override fun getBaseUrl(): String {
        return URL
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is NetProvider) 0 else 1
    }

}
