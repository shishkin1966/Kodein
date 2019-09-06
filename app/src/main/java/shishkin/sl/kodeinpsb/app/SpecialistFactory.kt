package shishkin.sl.kodeinpsb.app

import com.github.snowdream.android.util.Log
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.ISpecialistFactory
import shishkin.sl.kodeinpsb.sl.specialist.*

object SpecialistFactorySingleton {
    val instance = SpecialistFactory()
}

class SpecialistFactory : ISpecialistFactory {
    override fun create(name: String): ISpecialist? {
        return try {
            when (name) {
                ErrorSpecialist.NAME -> ErrorSpecialistSingleton.instance
                ApplicationSingleton.instance.getName() -> ApplicationSingleton.instance
                ActivityUnion.NAME -> ActivityUnion()
                PresenterUnion.NAME -> PresenterUnion()
                MessengerUnion.NAME -> MessengerUnion()
                ObservableUnion.NAME -> ObservableUnion()
                else -> Class.forName(name).newInstance() as ISpecialist
            }
        } catch (e: Exception) {
            Log.e("Can't create specialist with name \"name\":" + e.message)
            null
        }
    }
}
