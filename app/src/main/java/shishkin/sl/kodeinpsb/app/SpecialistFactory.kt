package shishkin.sl.kodeinpsb.app

import shishkin.sl.kodeinpsb.app.provider.DbProvider
import shishkin.sl.kodeinpsb.app.specialist.LocationUnion
import shishkin.sl.kodeinpsb.sl.INamed
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.ISpecialistFactory
import shishkin.sl.kodeinpsb.sl.specialist.*
import shishkin.sl.kodeinpsb.sl.task.CommonExecutor
import shishkin.sl.kodeinpsb.sl.task.DbExecutor
import shishkin.sl.kodeinpsb.sl.task.NetExecutor

object SpecialistFactorySingleton {
    val instance = SpecialistFactory()
}

class SpecialistFactory : ISpecialistFactory, INamed {
    companion object {
        const val NAME = "SpecialistFactory"
    }

    override fun create(name: String): ISpecialist? {
        return try {
            when (name) {
                ErrorSpecialist.NAME -> ErrorSpecialistSingleton.instance
                ApplicationSingleton.instance.getName() -> ApplicationSingleton.instance
                ActivityUnion.NAME -> ActivityUnion()
                PresenterUnion.NAME -> PresenterUnion()
                MessengerUnion.NAME -> MessengerUnion()
                ObservableUnion.NAME -> ObservableUnion()
                CommonExecutor.NAME -> CommonExecutor()
                DbExecutor.NAME -> DbExecutor()
                NetExecutor.NAME -> NetExecutor()
                DbProvider.NAME -> DbProvider()
                LocationUnion.NAME -> LocationUnion()
                else -> Class.forName(name).newInstance() as ISpecialist
            }
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(getName(), e)
            null
        }
    }

    override fun getName(): String {
        return NAME
    }

}
