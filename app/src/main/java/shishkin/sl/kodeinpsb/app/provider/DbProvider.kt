package shishkin.sl.kodeinpsb.app.provider

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.db.Db
import shishkin.sl.kodeinpsb.sl.provider.AbsDbProvider
import shishkin.sl.kodeinpsb.sl.request.IRequest
import shishkin.sl.kodeinpsb.sl.task.DbExecutor


class DbProvider : AbsDbProvider() {
    companion object {
        const val NAME = "DbProvider"
    }

    override fun request(request: IRequest) {
        if (!isValid()) return

        val executor = ApplicationSingleton.instance.get<DbExecutor>(DbExecutor.NAME);
        executor?.execute(request);
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
