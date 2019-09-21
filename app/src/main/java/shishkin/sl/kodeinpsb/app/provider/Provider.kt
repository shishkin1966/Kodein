package shishkin.sl.kodeinpsb.app.provider

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.request.GetAccountsRequest

class Provider {
    companion object {
        @JvmStatic
        fun getAccounts(subscriber: String) {
            ApplicationSingleton.instance.getDbProvider()?.request(GetAccountsRequest(subscriber))
        }
    }
}
