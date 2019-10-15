package shishkin.sl.kodeinpsb.app

import shishkin.sl.kodeinpsb.app.provider.DbProvider
import shishkin.sl.kodeinpsb.app.provider.LocationUnion
import shishkin.sl.kodeinpsb.app.provider.NetProvider
import shishkin.sl.kodeinpsb.app.provider.UseCasesProvider
import shishkin.sl.kodeinpsb.app.provider.notification.NotificationProvider
import shishkin.sl.kodeinpsb.sl.INamed
import shishkin.sl.kodeinpsb.sl.IProvider
import shishkin.sl.kodeinpsb.sl.IProviderFactory
import shishkin.sl.kodeinpsb.sl.provider.*
import shishkin.sl.kodeinpsb.sl.task.CommonExecutor
import shishkin.sl.kodeinpsb.sl.task.DbExecutor
import shishkin.sl.kodeinpsb.sl.task.NetExecutor

object ProviderFactorySingleton {
    val instance = ProviderFactory()
}

class ProviderFactory : IProviderFactory, INamed {
    companion object {
        const val NAME = "ProviderFactory"
    }

    override fun create(name: String): IProvider? {
        return try {
            when (name) {
                ErrorProvider.NAME -> ErrorSingleton.instance
                CrashProvider.NAME -> CrashProvider()
                ApplicationSingleton.instance.getName() -> ApplicationSingleton.instance
                ActivityUnion.NAME -> ActivityUnion()
                PresenterUnion.NAME -> PresenterUnion()
                MessengerUnion.NAME -> MessengerUnion()
                ObservableUnion.NAME -> ObservableUnion()
                CommonExecutor.NAME -> CommonExecutor()
                DbExecutor.NAME -> DbExecutor()
                DbProvider.NAME -> DbProvider()
                NetExecutor.NAME -> NetExecutor()
                NetProvider.NAME -> NetProvider()
                LocationUnion.NAME -> LocationUnion()
                CacheProvider.NAME -> CacheProvider()
                UseCasesProvider.NAME -> UseCasesProvider()
                NotificationProvider.NAME -> NotificationProvider()
                else -> Class.forName(name).newInstance() as IProvider
            }
        } catch (e: Exception) {
            ErrorSingleton.instance.onError(getName(), e)
            null
        }
    }

    override fun getName(): String {
        return NAME
    }

}
