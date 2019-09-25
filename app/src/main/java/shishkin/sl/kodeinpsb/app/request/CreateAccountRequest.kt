package shishkin.sl.kodeinpsb.app.request

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.db.Db
import shishkin.sl.kodeinpsb.sl.observe.ObjectObservable
import shishkin.sl.kodeinpsb.sl.request.AbsRequest


class CreateAccountRequest(private val account: Account) : AbsRequest() {
    companion object {
        const val NAME = "CreateAccountRequest"
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    override fun run() {
        account.openDate = System.currentTimeMillis()
        val db = ApplicationSingleton.instance.getDbProvider()!!.getDb<Db>()
        if (db != null) {
            try {
                db.getDao().insertAccount(account)
                val observable =
                    ApplicationSingleton.instance.getObservableUnion()?.getObservable(
                        ObjectObservable.NAME
                    ) as ObjectObservable?
                observable?.onChange(Account.TABLE)
            } catch (e: Exception) {
                ApplicationSingleton.instance.onError(getName(), e)
            }
        }
    }

}
