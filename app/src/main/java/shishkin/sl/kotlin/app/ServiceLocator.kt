package shishkin.sl.kotlin.app

import shishkin.sl.kotlin.sl.AbsServiceLocator
import shishkin.sl.kotlin.sl.IProviderFactory
import shishkin.sl.kotlin.sl.observe.NetObservable
import shishkin.sl.kotlin.sl.observe.ObjectObservable
import shishkin.sl.kotlin.sl.observe.ScreenBroadcastReceiverObservable
import shishkin.sl.kotlin.sl.provider.CrashProvider
import shishkin.sl.kotlin.sl.provider.ErrorSingleton
import shishkin.sl.kotlin.sl.provider.IObservableUnion
import shishkin.sl.kotlin.sl.provider.ObservableUnion

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

        registerProvider(ObservableUnion.NAME)
        val union = get<IObservableUnion>(ObservableUnion.NAME)
        union?.register(NetObservable())
        union?.register(ScreenBroadcastReceiverObservable())
        union?.register(ObjectObservable())
    }

    override fun getProviderFactory(): IProviderFactory {
        return ProviderFactorySingleton.instance
    }

}
