package shishkin.sl.kotlin.app.request

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Account
import shishkin.sl.kotlin.app.db.Db
import shishkin.sl.kotlin.sl.observe.ObjectObservable
import shishkin.sl.kotlin.sl.request.AbsRequest


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
        val db = ApplicationSingleton.instance.getDbProvider().getDb<Db>()
        if (db != null) {
            try {
                db.getDao().insertAccount(account)
                val observable =
                    ApplicationSingleton.instance.getObservableUnion().getObservable(
                        ObjectObservable.NAME
                    ) as ObjectObservable?
                observable?.onChange(Account.TABLE)
            } catch (e: Exception) {
                ApplicationSingleton.instance.onError(getName(), e)
            }
        }
    }

}
