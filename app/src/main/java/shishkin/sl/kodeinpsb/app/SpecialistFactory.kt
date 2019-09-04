package shishkin.sl.kodeinpsb.app

import com.github.snowdream.android.util.Log
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.ISpecialistFactory
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialistSingleton

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
            } else {
                Class.forName(name).newInstance() as ISpecialist
            }
        } catch (e: Exception) {
            Log.e("Can't create specialist with name \"name\":" + e.message)
            return null
        }
    }
}