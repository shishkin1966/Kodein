package shishkin.sl.kodeinpsb.sl.specialist

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import shishkin.sl.kodeinpsb.BuildConfig
import shishkin.sl.kodeinpsb.sl.ISpecialist

class ApplicationSpecialist : Application(), IApplicationSpecialist {
    private var isExit = false
    val kodein = Kodein {
        bind<IApplicationSpecialist>() with singleton { instance }
        bind<IErrorSpecialist>() with singleton { ErrorSpecialistSingleton.instance }
    }

    companion object {
        val instance = ApplicationSpecialist()
    }

    override fun isPersistent(): Boolean {
        return true
    }

    override fun onUnRegister() {
    }

    override fun onRegister() {
    }

    override fun stop() {
    }

    override fun getName(): String {
        return BuildConfig.APPLICATION_ID
    }

    override fun validate(): Boolean {
        return !isExit
    }

    override fun compareTo(other: ISpecialist): Int {
        return if (other is IApplicationSpecialist) 0 else 1
    }

    override fun isExit(): Boolean {
        return isExit
    }

    /**
     * выйти из приложеня
     */
    override fun exit() {
        stop()
    }
}