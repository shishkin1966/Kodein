package shishkin.sl.kodeinpsb.app.provider

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.db.Db
import shishkin.sl.kodeinpsb.sl.ISubscriber

class Provider {
    companion object {
        @JvmStatic
        fun getAcoounts(subscriber: ISubscriber) {
            val db = ApplicationSingleton.instance.getDbProvider()?.getDb<Db>() as Db
            val accounts = db.getDao().getAccounts()
        }
    }
}