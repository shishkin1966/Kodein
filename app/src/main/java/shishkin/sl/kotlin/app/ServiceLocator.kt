package shishkin.sl.kotlin.app

import shishkin.sl.kotlin.app.provider.DbProvider
import shishkin.sl.kotlin.app.provider.NetProvider
import shishkin.sl.kotlin.sl.AbsServiceLocator
import shishkin.sl.kotlin.sl.IProviderFactory
import shishkin.sl.kotlin.sl.observe.NetObservable
import shishkin.sl.kotlin.sl.observe.ObjectObservable
import shishkin.sl.kotlin.sl.observe.ScreenBroadcastReceiverObservable
import shishkin.sl.kotlin.sl.provider.*
import shishkin.sl.kotlin.sl.task.CommonExecutor
import shishkin.sl.kotlin.sl.task.DbExecutor
import shishkin.sl.kotlin.sl.task.NetExecutor

object ServiceLocatorSingleton {
    val instance = ServiceLocator()
}

class ServiceLocator : AbsServiceLocator() {
    companion object {
        const val NAME = "ServiceLocator"
    }


    override fun getName(): String {
        return NAME
    }

    override fun start() {
        registerProvider(ErrorSingleton.instance)
        registerProvider(ApplicationSingleton.instance)
        registerProvider(CrashProvider.NAME)

        registerProvider(CacheProvider.NAME)
        registerProvider(ObservableUnion.NAME)
        val union = get<IObservableUnion>(ObservableUnion.NAME)
        union?.register(NetObservable())
        union?.register(ScreenBroadcastReceiverObservable())
        union?.register(ObjectObservable())

        registerProvider(DbExecutor.NAME)
        registerProvider(DbProvider.NAME)

        registerProvider(NetExecutor.NAME)
        registerProvider(NetProvider.NAME)

        registerProvider(CommonExecutor.NAME)
    }

    override fun getProviderFactory(): IProviderFactory {
        return ProviderFactorySingleton.instance
    }

}
