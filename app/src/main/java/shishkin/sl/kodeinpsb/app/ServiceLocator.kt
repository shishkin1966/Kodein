package shishkin.sl.kodeinpsb.app

import shishkin.sl.kodeinpsb.sl.AbsServiceLocator
import shishkin.sl.kodeinpsb.sl.ISpecialistFactory
import shishkin.sl.kodeinpsb.sl.observe.NetObservable
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialistSingleton
import shishkin.sl.kodeinpsb.sl.specialist.IObservableUnion
import shishkin.sl.kodeinpsb.sl.specialist.ObservableUnion

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

        registerSpecialist(ObservableUnion.NAME)
        val union = get<IObservableUnion>(ObservableUnion.NAME)
        union?.register(NetObservable())
    }

    override fun getSpecialistFactory(): ISpecialistFactory {
        return SpecialistFactorySingleton.instance
    }

}
