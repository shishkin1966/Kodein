package shishkin.sl.kodeinpsb.app

import shishkin.sl.kodeinpsb.sl.AbsServiceLocator
import shishkin.sl.kodeinpsb.sl.ISpecialistFactory
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialistSingleton

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
    }

    override fun getSpecialistFactory(): ISpecialistFactory {
        return SpecialistFactorySingleton.instance
    }

}
