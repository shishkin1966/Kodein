package shishkin.sl.kotlin.app

import shishkin.sl.kotlin.app.provider.DbProvider
import shishkin.sl.kotlin.app.provider.LocationUnion
import shishkin.sl.kotlin.app.provider.NetProvider
import shishkin.sl.kotlin.app.provider.notification.NotificationProvider
import shishkin.sl.kotlin.sl.INamed
import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.IProviderFactory
import shishkin.sl.kotlin.sl.provider.*
import shishkin.sl.kotlin.sl.task.CommonExecutor
import shishkin.sl.kotlin.sl.task.DbExecutor
import shishkin.sl.kotlin.sl.task.NetExecutor

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
