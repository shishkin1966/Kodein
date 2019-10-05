package shishkin.sl.kodeinpsb.app

import shishkin.sl.kodeinpsb.app.provider.DbProvider
import shishkin.sl.kodeinpsb.app.provider.NetProvider
import shishkin.sl.kodeinpsb.app.specialist.UseCasesSpecialist
import shishkin.sl.kodeinpsb.sl.AbsServiceLocator
import shishkin.sl.kodeinpsb.sl.ISpecialistFactory
import shishkin.sl.kodeinpsb.sl.observe.NetObservable
import shishkin.sl.kodeinpsb.sl.observe.ObjectObservable
import shishkin.sl.kodeinpsb.sl.observe.ScreenBroadcastReceiverObservable
import shishkin.sl.kodeinpsb.sl.specialist.*
import shishkin.sl.kodeinpsb.sl.task.CommonExecutor
import shishkin.sl.kodeinpsb.sl.task.DbExecutor
import shishkin.sl.kodeinpsb.sl.task.NetExecutor

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
        registerSpecialist(ErrorSpecialistSingleton.instance)
        registerSpecialist(ApplicationSingleton.instance)
        registerSpecialist(CrashSpecialist.NAME)

        registerSpecialist(CacheSpecialist.NAME)
        registerSpecialist(ObservableUnion.NAME)
        val union = get<IObservableUnion>(ObservableUnion.NAME)
        union?.register(NetObservable())
        union?.register(ScreenBroadcastReceiverObservable())

        union?.register(ObjectObservable())

        registerSpecialist(DbExecutor.NAME)
        registerSpecialist(DbProvider.NAME)

        registerSpecialist(NetExecutor.NAME)
        registerSpecialist(NetProvider.NAME)

        registerSpecialist(CommonExecutor.NAME)
        registerSpecialist(UseCasesSpecialist.NAME)
    }

    override fun getSpecialistFactory(): ISpecialistFactory {
        return SpecialistFactorySingleton.instance
    }

}
