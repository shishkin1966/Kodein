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
        try {
            return if (name.equals(ErrorSpecialist.NAME)) {
                ErrorSpecialistSingleton.instance
            } else if (name.equals(ApplicationSingleton.instance.getName())) {
                ApplicationSingleton.instance
            } else if (name.equals(ActivityUnion.NAME)) {
                ActivityUnion()
            } else if (name.equals(PresenterUnion.NAME)) {
                PresenterUnion()
            } else if (name.equals(MessengerUnion.NAME)) {
                MessengerUnion()
            } else {
                Class.forName(name).newInstance() as ISpecialist
            }
        } catch (e: Exception) {
            Log.e("Can't create specialist with name \"name\":" + e.message)
            return null
        }
    }
}