package shishkin.sl.kotlin.app.provider

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.db.Db
import shishkin.sl.kotlin.sl.provider.AbsDbProvider
import shishkin.sl.kotlin.sl.request.IRequest


class DbProvider : AbsDbProvider() {
    companion object {
        const val NAME = "DbProvider"
    }

    override fun request(request: IRequest) {
        if (!isValid()) return

        ApplicationSingleton.instance.executeDb(request)
    }

    override fun onRegister() {
        super.getDb(
            Db::class.java,
            Db.NAME
        )
    }

    override fun getName(): String {
        return NAME
    }

}
